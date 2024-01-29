import React, { useRef, useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import * as StompJs from "@stomp/stompjs";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";

function ChatApp() {
  const [chatList, setChatList] = useState([]);
  const [chat, setChat] = useState("");
  const { apply_id } = useParams();
  const [userId, setUserId] = useState("현재 사용자 ID"); // 사용자 ID 설정
  const client = useSelector((store) => store.user.userName);

  // 채팅 내용을 서버에서 불러오는 함수
  const fetchChat = async () => {
    axiosApi
      .get(`/rooms/chat`) // 실제 URL로 변경 필요
      .then((res) => {
        setChatList(res.data);
      })
      .catch((error) => {
        console.error("채팅 내용을 불러오는 중 에러가 발생했습니다.", error);
      });
  };

  // 메시지 입력 시 호출되는 함수
  const handleChange = (event) => {
    setChat(event.target.value);
  };

  // 메시지 전송 시 호출되는 함수
  const handleSubmit = (event) => {
    event.preventDefault();
    publish(chat);
  };

  // WebSocket 연결 설정 함수
  const connect = () => {
    client.current = new StompJs.Client({
      brokerURL: "ws://localhost:80/ws", // 실제 WebSocket 서버 URL로 변경 필요
      onConnect: () => {
        console.log("WebSocket 연결 성공");
        subscribe();
      },
      onStompError: (error) => {
        console.error("WebSocket 연결 오류", error);
      },
    });
    client.current.activate();
  };

  // 메시지 구독 설정 함수
  const subscribe = () => {
    client.current.subscribe("/sub/chat/" + apply_id, (message) => {
      const json_message = JSON.parse(message.body);
      setChatList((prevList) => [...prevList, json_message]);
    });
  };

  // 메시지 전송 함수
  const publish = (chat) => {
    if (!client.current.connected) return;

    client.current.publish({
      destination: "/pub/chat",
      body: JSON.stringify({ applyId: apply_id, senderId: userId, text: chat }),
    });

    setChat("");
  };

  // 연결 해제 함수
  const disconnect = () => {
    if (client.current.connected) {
      client.current.deactivate();
    }
  };

  // 채팅 메시지 UI 렌더링 함수
  const renderChatMessages = () => {
    return chatList.map((message, index) => (
      <div
        key={index}
        style={{ textAlign: message.senderId === userId ? "right" : "left" }}
      >
        <div
          style={{
            display: "inline-block",
            margin: "5px",
            padding: "10px",
            backgroundColor: "#f0f0f0",
            borderRadius: "10px",
          }}
        >
          {message.text}
        </div>
      </div>
    ));
  };

  useEffect(() => {
    fetchChat();
    connect();

    return () => disconnect();
  }, []);

  return (
    <div>
      <div
        style={{
          overflow: "auto",
          height: "400px",
          border: "1px solid #ccc",
          padding: "10px",
          marginBottom: "10px",
        }}
      >
        {renderChatMessages()}
      </div>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={chat}
          onChange={handleChange}
          style={{ width: "80%", marginRight: "10px" }}
        />
        <button type="submit">보내기</button>
      </form>
    </div>
  );
}

export default ChatApp;
