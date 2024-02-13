import React, { useState, useEffect, useRef } from 'react';

function MeetingLobby() {
  const [devices, setDevices] = useState({ audio: [], video: [] });
  const [selectedAudioDevice, setSelectedAudioDevice] = useState('');
  const [selectedVideoDevice, setSelectedVideoDevice] = useState('');
  const videoRef = useRef(null); // 비디오 태그를 위한 ref

  useEffect(() => {
    navigator.mediaDevices.enumerateDevices().then((deviceInfos) => {
      const audioDevices = deviceInfos.filter(device => device.kind === 'audioinput');
      const videoDevices = deviceInfos.filter(device => device.kind === 'videoinput');
      setDevices({ audio: audioDevices, video: videoDevices });

      if (audioDevices.length > 0) {
        setSelectedAudioDevice(audioDevices[0].deviceId);
      }
      if (videoDevices.length > 0) {
        setSelectedVideoDevice(videoDevices[0].deviceId);
      }
    });
  }, []);

  useEffect(() => {
    // 선택된 비디오 디바이스 변경 시 미리보기 업데이트
    if (selectedVideoDevice) {
      updateVideoPreview(selectedVideoDevice);
    }
  }, [selectedVideoDevice]);

  const handleAudioDeviceChange = (event) => {
    setSelectedAudioDevice(event.target.value);
  };

  const handleVideoDeviceChange = (event) => {
    setSelectedVideoDevice(event.target.value);
  };

  const updateVideoPreview = (deviceId) => {
    if (videoRef.current) {
      navigator.mediaDevices.getUserMedia({
        video: { deviceId: { exact: deviceId } },
        audio: false, // 오디오는 미리보기에 포함하지 않음
      }).then((stream) => {
        videoRef.current.srcObject = stream;
      }).catch((error) => {
        console.error("Error accessing the camera", error);
      });
    }
  };

  return (
    <div className="meeting-lobby">
      <h2>미팅 대기방</h2>
      <p>미팅에 참여하기 전에 마이크와 카메라를 선택해주세요.</p>
      <div>
        <label htmlFor="audioSelect">마이크 선택:</label>
        <select id="audioSelect" value={selectedAudioDevice} onChange={handleAudioDeviceChange}>
          {devices.audio.map((device, index) => (
            <option key={index} value={device.deviceId}>{device.label || `Microphone ${index + 1}`}</option>
          ))}
        </select>
      </div>
      <div>
        <label htmlFor="videoSelect">카메라 선택:</label>
        <select id="videoSelect" value={selectedVideoDevice} onChange={handleVideoDeviceChange}>
          {devices.video.map((device, index) => (
            <option key={index} value={device.deviceId}>{device.label || `Camera ${index + 1}`}</option>
          ))}
        </select>
      </div>
      <div>
        <video ref={videoRef} autoPlay playsInline muted></video>
      </div>
    </div>
  );
}

export default MeetingLobby;
