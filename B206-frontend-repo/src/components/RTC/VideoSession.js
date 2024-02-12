import React, { useState, useEffect } from 'react';
import { OpenVidu } from 'openvidu-browser';
import axios from 'axios';

const OV = new OpenVidu();

function VideoSession() {
    const [session, setSession] = useState(null);
    const [publisher, setPublisher] = useState(null);

    useEffect(() => {
        createSession();
        return () => {
            leaveSession();
        };
    }, []);

    const createSession = async () => {
        try {
            // Axios를 사용하여 POST 요청을 보냅니다.
            const response = await axios.post('http://localhost:80/openvidu/api/sessions');
            const { sessionId, token } = response.data;
            joinSession(sessionId, token);
        } catch (error) {
            console.error('Error creating session:', error);
        }
    };

    const joinSession = (sessionId, token) => {
        const session = OV.initSession();

        session.on('streamCreated', (event) => {
            const subscriber = session.subscribe(event.stream, 'video-container');
        });

        session.connect(token)
            .then(() => {
                const publisher = OV.initPublisher('video-container', {
                    audioSource: undefined, // The source of audio. If undefined default microphone
                    videoSource: undefined, // The source of video. If undefined default webcam
                    publishAudio: true,     // Whether you want to start publishing with your audio unmuted or not
                    publishVideo: true      // Whether you want to start publishing with your video enabled or not
                });
                session.publish(publisher);
                setSession(session);
                setPublisher(publisher);
            })
            .catch(error => {
                console.error('There was an error connecting to the session:', error);
            });
    };

    const leaveSession = () => {
        if (session) {
            session.disconnect();
        }
        // Clean up state
        setSession(null);
        setPublisher(null);
    };

    return (
        <div>
            <div id="video-container"></div>
            {/* Additional UI components for controlling the session */}
        </div>
    );
}

export default VideoSession;
