// ChatApp.js
import React, { useEffect, useState, useRef } from "react";
import { useParams } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";
import { Stomp } from "@stomp/stompjs";

function ChatApp() {
  const { roomId } = useParams(); // URL에서 채팅방 ID 추출
  const [messages, setMessages] = useState([]); // 채팅 메시지 상태
  const [message, setMessage] = useState(""); // 메시지 입력 상태
  const stompClient = useRef(null); // STOMP 클라이언트 참조
  const currentUser = useSelector((state) => state.user); // 현재 로그인한 사용자 정보

  useEffect(() => {
    connect(); // 웹소켓 연결
    fetchMessages(); // 기존 메시지 가져오기

    return () => disconnect(); // 컴포넌트 언마운트 시 연결 해제
  }, [roomId]);

  const connect = () => {
    const socket = new WebSocket("ws://localhost:80/ws");
    stompClient.current = Stomp.over(socket);
    stompClient.current.connect({}, () => {
      stompClient.current.subscribe(`/sub/chat/room/${roomId}`, (message) => {
        const newMessage = JSON.parse(message.body);
        setMessages((prevMessages) => [...prevMessages, newMessage]);
      });
    });
  };

  const disconnect = () => {
    if (stompClient.current) {
      stompClient.current.disconnect();
    }
  };

  const fetchMessages = () => {
    axiosApi
      .get(`/chat/messages/${roomId}`)
      .then((response) => setMessages(response.data))
      .catch((error) =>
        console.error("채팅 메시지를 가져오는데 실패했습니다.", error)
      );
  };

  const sendMessage = () => {
    if (stompClient.current && message) {
      const messageObj = {
        roomId,
        sender: currentUser.userName,
        message: message,
      };
      stompClient.current.send(
        `/pub/chat/message`,
        {},
        JSON.stringify(messageObj)
      );
      setMessage(""); // 상태를 이용한 입력 필드 초기화
    }
  };

  return (
    <div>
      <div>
        {messages.map((msg, index) => (
          <div
            key={index}
            style={{
              textAlign: msg.sender === currentUser.userName ? "right" : "left",
            }}
          >
            {msg.sender !== currentUser.userName && <p>{msg.sender}</p>}
            <p>{msg.message}</p>
          </div>
        ))}
      </div>
      <div>
        <input
          type="text"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
        />
        <button onClick={sendMessage}>보내기</button>
      </div>
    </div>
  );
}

export default ChatApp;
