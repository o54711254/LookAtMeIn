import React from "react";
import { Link } from "react-router-dom";

function ChatRoom({ chatRooms }) {
  return (
    <div>
      {chatRooms.map((room) => (
        <Link key={room.id} to={"chat-app/" + room.id}>
          {room.name}
        </Link>
      ))}
    </div>
  );
}

export default ChatRoom;
