import React, { useState, useEffect, useRef } from 'react';
import { initializeApp } from 'firebase/app';
import { getFirestore, doc, setDoc, onSnapshot, collection, addDoc, getDoc} from 'firebase/firestore';
// import styles from './Peer.module.css'; // CSS 모듈 임포트

const firebaseConfig = {
    apiKey: "AIzaSyB1XuVmay25vZVzGzhF7DCAHl161KecOSI",
    authDomain: "lookatmein-d5fb1.firebaseapp.com",
    projectId: "lookatmein-d5fb1",
    storageBucket: "lookatmein-d5fb1.appspot.com",
    messagingSenderId: "444229246627",
    appId: "1:444229246627:web:9df5ae5f7333eeb05da916"
};

const app = initializeApp(firebaseConfig);
const firestore = getFirestore(app);

const servers = {
    iceServers: [
        { urls: ['stun:stun1.l.google.com:19302', 'stun:stun2.l.google.com:19302'] },
    ],
    iceCandidatePoolSize: 10,
};

const Peer = () => {
    const [callId, setCallId] = useState('');
    const localVideoRef = useRef(null);
    const remoteVideoRef = useRef(null);
    const pc = useRef(new RTCPeerConnection(servers));

    useEffect(() => {
        const setupLocalStream = async () => {
            const localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
            localVideoRef.current.srcObject = localStream;

            localStream.getTracks().forEach((track) => {
                pc.current.addTrack(track, localStream);
            });
        };

        const remoteStream = new MediaStream();
        remoteVideoRef.current.srcObject = remoteStream;

        pc.current.ontrack = (event) => {
            event.streams[0].getTracks().forEach((track) => {
                remoteStream.addTrack(track);
            });
        };

        setupLocalStream();

        return () => {
            pc.current.close();
        };
    }, []);

    const createCall = async () => {
        const callDocRef = doc(collection(firestore, 'calls'));
        const offerCandidatesCollection = collection(callDocRef, 'offerCandidates');
        const answerCandidatesCollection = collection(callDocRef, 'answerCandidates');

        setCallId(callDocRef.id);

        pc.current.onicecandidate = (event) => {
            event.candidate && addDoc(offerCandidatesCollection, event.candidate.toJSON());
        };

        const offerDescription = await pc.current.createOffer();
        await pc.current.setLocalDescription(offerDescription);

        const offer = {
            sdp: offerDescription.sdp,
            type: offerDescription.type,
        };

        await setDoc(callDocRef, { offer });

        onSnapshot(callDocRef, (snapshot) => {
            const data = snapshot.data();
            if (!pc.current.currentRemoteDescription && data?.answer) {
                const answerDescription = new RTCSessionDescription(data.answer);
                pc.current.setRemoteDescription(answerDescription);
            }
        });

        onSnapshot(answerCandidatesCollection, (snapshot) => {
            snapshot.docChanges().forEach((change) => {
                if (change.type === 'added') {
                    const candidate = new RTCIceCandidate(change.doc.data());
                    pc.current.addIceCandidate(candidate);
                }
            });
        });
    };

    const answerCall = async () => {
        const callDocRef = doc(firestore, 'calls', callId);
        const answerCandidatesCollection = collection(callDocRef, 'answerCandidates');
        const offerCandidatesCollection = collection(callDocRef, 'offerCandidates');

        pc.current.onicecandidate = (event) => {
            event.candidate && addDoc(answerCandidatesCollection, event.candidate.toJSON());
        };

        const callData = (await getDoc(callDocRef)).data();
        const offerDescription = callData.offer;
        await pc.current.setRemoteDescription(new RTCSessionDescription(offerDescription));

        const answerDescription = await pc.current.createAnswer();
        await pc.current.setLocalDescription(answerDescription);

        const answer = {
            type: answerDescription.type,
            sdp: answerDescription.sdp,
        };

        await setDoc(callDocRef, { answer }, { merge: true });

        onSnapshot(offerCandidatesCollection, (snapshot) => {
            snapshot.docChanges().forEach((change) => {
                if (change.type === 'added') {
                    const candidate = new RTCIceCandidate(change.doc.data());
                    pc.current.addIceCandidate(candidate);
                }
            });
        });
    };

    return (
        <div>
            <video ref={localVideoRef} autoPlay playsInline />
            <video ref={remoteVideoRef} autoPlay playsInline />
            <button onClick={createCall}>Create Call</button>
            <input value={callId} onChange={(e) => setCallId(e.target.value)} placeholder="Enter Call ID" />
            <button onClick={answerCall}>Answer</button>
        </div>
    );
};

export default Peer;
