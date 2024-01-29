import React from "react";
import { BrowserRouter, Link, Route, Routes } from "react-router-dom";
import ChatApp from "./ChatApp";
import { useSelector } from "react-redux";

function ChatRoom({ chatRooms }) {
  // props로 채팅방 목록 받음
  return (
    <div>
      <BrowserRouter>
        {chatRooms.map((room) => (
          <Link key={room.id} to={"/chat-app/" + room.id}>
            {room.name}
          </Link> // 각 채팅방 링크
        ))}
        <Routes>
          <Route path="/chat-app/:apply_id" element={<ChatApp />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default ChatRoom;
