import React from "react";
import { useLocation } from "react-router-dom";
import VideoRoomComponent from "../openvidu/components/VideoRoomComponent";
import { useSelector } from "react-redux";

function OpenViduRoom() {
  const location = useLocation();
  console.log(location.state);
  const role = useSelector((state)=> state.user.role);
  const hosSeq = 1;
  const userSeq = 2;
  console.log(hosSeq);
  return (
    <div>
      <VideoRoomComponent
      role={role}
      hosSeq={hosSeq}
      userSeq={userSeq}
      />
    </div>
  );
}

export default OpenViduRoom;
