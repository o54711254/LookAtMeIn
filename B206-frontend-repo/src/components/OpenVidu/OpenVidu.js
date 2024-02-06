import React from "react";
import { useLocation } from "react-router-dom";
import VideoRoom from "../OpenVidu/streaming/VideoComponent";

function OpenViduRoom() {
  const location = useLocation();
  console.log(location.state);
  return (
    <div>
      <VideoRoom
        // hospital={location.state.hospitalSeq}
        // client={location.state.userSeq}
        // reservationSeq={location.state.reservationSeq}
        // who={location.state.who}
        hospital="12"
        client="1"
        reservationSeq="1"
        who="1"
      />
    </div>
  );
}

export default OpenViduRoom;
