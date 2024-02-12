import React, { useEffect, useState } from 'react';
import { OpenVidu } from 'openvidu-browser';

const OV = new OpenVidu();
let session;

function VideoSession() {
  const [sessionId, setSessionId] = useState('');
  const [token, setToken] = useState('');

  useEffect(() => {
    joinSession();
    return () => {
      leaveSession();
    };
  }, []);

  const joinSession = async () => {
    try {
      const response = await fetch('/api/sessions', { method: 'POST' });
      const data = await response.json();
      setSessionId(data.sessionId);
      setToken(data.token);

      session = OV.initSession();

      session.on('streamCreated', (event) => {
        const subscriber = session.subscribe(event.stream, undefined);
        document.getElementById('video-container').appendChild(subscriber.videoElement);
      });

      await session.connect(data.token);

      let publisher = OV.initPublisher(undefined, {
        audioSource: undefined, // The source of audio. If undefined default microphone
        videoSource: undefined, // The source of video. If undefined default webcam
        publishAudio: true,   // Whether you want to start publishing with your audio unmuted or not
        publishVideo: true,   // Whether you want to start publishing with your video enabled or not
        resolution: '640x480',  // The resolution of your video
        frameRate: 30,  // The frame rate of your video
        insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
      });

      session.publish(publisher);
    } catch (error) {
      console.error('Failed to join session:', error);
    }
  };

  const leaveSession = () => {
    if (session) {
      session.disconnect();
    }
  };

  return (
    <div>
      <div id="video-container"></div>
    </div>
  );
}

export default VideoSession;
