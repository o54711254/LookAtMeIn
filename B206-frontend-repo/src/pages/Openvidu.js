import React from "react";
import { useLocation } from "react-router-dom";
import VideoRoomComponent from "../openvidu/components/VideoRoomComponent";

function OpenViduRoom() {
  const location = useLocation();
  console.log(location.state);
  return (
    <div>
      <VideoRoomComponent/>
    </div>
  );
}

export default OpenViduRoom;
