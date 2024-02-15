import React, { useEffect, useRef, useState, useSyncExternalStore } from 'react';
import styles from './waiting.module.css'; // 스타일 파일 경로는 프로젝트에 맞게 조정하세요
import { useLocation, Link } from "react-router-dom";

function VideoCallComponent({ role }) {
    const currentUserVideoRef = useRef(null);
    const peer = useRef(null);
    const location = useLocation();
    const { meetingData } = location.state || {};

    useEffect(() => {
        // peer.current = new Peer({
        //     host: 'localhost',
        //     port: 80,
        //     path: '/myapp'
        // });

        console.log(meetingData.reserveSeq)
        // 사용자의 비디오 스트림 요청
        navigator.mediaDevices.getUserMedia({ video: true, audio: true })
            .then((stream) => {
                if (currentUserVideoRef.current) {
                    currentUserVideoRef.current.srcObject = stream;
                }
            })
            .catch((err) => {
                console.error('Failed to get local stream', err);
            });
        }
    );


    return (
        <div>
            <div className={styles.div}>
                <div>
                    <video ref={currentUserVideoRef} autoPlay playsInline muted style={{width: '100%', height:'500px'}} />
                </div>
                    <div className={styles.alert}>
                        {role === "CUSTOMER" ? (
                            <div className={styles.contentbox}>
                                <p className={styles.title}>상담 이용 안내</p>
                                <p className={styles.content}>1. 상담 결과는 외부에 공개할 수 없으며, 위반 시 사이트 이용에 제한이 있을 수 있습니다.</p>
                                <p className={styles.content}>2. 상담사에게 폭언 및 욕설 등 부적절한 발언 시 처벌 받을 수 있습니다.</p>
                                <p className={styles.content}>3. 본 상담을 통해 받은 견적은 실제 시술 비용과 상이할 수 있습니다.</p>
                                <p className={styles.content}>4. 카메라와 마이크 세팅이 끝났다면 아래의 입장하기 버튼을 눌러 상담에 입장해 주세요.</p>
                                <div className={styles.enterbox}>
                                <Link to={`/meeting/${meetingData.reserveSeq}`} className={styles.enter}>
                                입장하기
                            </Link>
                                </div>
                            </div>
                        ) : (
                            <div className={styles.contentbox}>
                            <p className={styles.title}>
                               상담 이용 안내
                            </p>
                            <h1 className={styles.content}>1.  상담 결과는 외부에 공개 할 수 없으며, 위반 시 사이트 이용에 제한이 있을 수 있습니다.</h1>
                            <p className={styles.content}>
                            2. 고객에게 폭언 및 욕설 등 부적절한 발언 시 처벌 받을 수 있습니다.
                            </p>
                            <p className={styles.content}>
                            3. 고객에게 과도한 과장 광고 및 부적절한 정보 전달은 제재의 대상이 될 수 있습니다.
                            </p>
                            <p className={styles.content}>
                            4. 카메라와 마이크 세팅이 끝났다면 아래의 입장하기 버튼을 눌러 상담에 입장해 주세요.
                            </p>
                        <div className={styles.enterbox}>
                        <Link to={`/meeting/${meetingData.reserveSeq}`} className={styles.enter}>
                                입장하기
                            </Link>
                          </div>
                          </div>
                        )}
                    </div>
            </div>
        </div>
    );
}

export default VideoCallComponent;
