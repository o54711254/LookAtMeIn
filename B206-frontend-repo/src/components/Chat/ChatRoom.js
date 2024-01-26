import React from "react";
import "./ChatRoom.module.css"; // CSS 파일을 이렇게 import합니다.

function ChatRoom() {
  const chatRooms = [
    // 여기에 채팅방 목록 데이터를 넣습니다. 예를 들면:
    {
      name: "룩앳미인",
      message: "아 진짜 할게 매우 많구나ㅋㅋ 밤을 새..",
      unreadCount: 1,
    },
    // 다른 채팅방 데이터도 추가...
  ];

  return (
    <div className="chat-room-list">
      {chatRooms.map((room, index) => (
        <div key={index} className="chat-room">
          <div
            className="profile-image"
            style={{ backgroundImage: `url(${room.image})` }}
          ></div>
          <div className="room-info">
            <div className="name">{room.name}</div>
            <div className="last-message">{room.message}</div>
          </div>
          {room.unreadCount > 0 && (
            <div className="unread-count">{room.unreadCount}</div>
          )}
        </div>
      ))}
    </div>
  );
}

export default ChatRoom;
