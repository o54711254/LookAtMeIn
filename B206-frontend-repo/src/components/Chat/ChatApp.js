import React, { useEffect, useState, useRef } from "react";
import { useParams } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";
import { Stomp } from "@stomp/stompjs";
import Reserve from "../Modal/DateTimePickerModalForChat";
function ChatApp() {
  // URL에서 채팅방 ID를 가져옴
  const { roomId } = useParams();
  // 채팅 메시지 상태
  const [messages, setMessages] = useState([]);
  // 메시지 입력 상태
  const [message, setMessage] = useState("");
  // STOMP 클라이언트를 위한 ref. 웹소켓 연결을 유지하기 위해 사용
  const stompClient = useRef(null);
  // Redux store에서 현재 사용자 정보 가져오기
  const currentUser = useSelector((state) => state.user);
  // 채팅 메시지 목록의 끝을 참조하는 ref. 이를 이용해 새 메시지가 추가될 때 스크롤을 이동
  const messagesEndRef = useRef(null);
  // 컴포넌트 마운트 시 실행. 웹소켓 연결 및 초기 메시지 로딩
  const [profileImg, setProfileImg] = useState(null);
  const [customerSeq, setCustomerSeq] = useState("");

  useEffect(() => {
    connect();
    fetchMessages();
    // 컴포넌트 언마운트 시 웹소켓 연결 해제
    return () => disconnect();
  }, [roomId]);
  // 메시지 목록이 업데이트될 때마다 스크롤을 최하단으로 이동시키는 함수
  useEffect(() => {
    scrollToBottom();
  }, [messages]);
  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };
  // 웹소켓 연결 설정
  const connect = () => {
    const socket = new WebSocket("wss://i10b206.p.ssafy.io/ws");
    stompClient.current = Stomp.over(socket);
    stompClient.current.connect({}, () => {
      stompClient.current.subscribe(`/sub/chatroom/${roomId}`, (message) => {
        const newMessage = JSON.parse(message.body);
        setMessages((prevMessages) => [...prevMessages, newMessage]);

        if (newMessage.senderSeq !== currentUser.userSeq) {
          setCustomerSeq(newMessage.senderSeq);
        }
      });
    });
    console.log("방 번호", roomId);
  };
  // 웹소켓 연결 해제
  const disconnect = () => {
    if (stompClient.current) {
      stompClient.current.disconnect();
    }
  };
  // 기존 채팅 메시지를 서버로부터 가져오는 함수
  const fetchMessages = () => {
    axiosApi
      .get(`/chatroom/${roomId}/messages`)
      .then((response) => {
        console.log("메시지 목록", response.data);
        const customerProfileBase64 = response.data.customerProfileBase64;
        const customerProfileType = response.data.customerProfileType;
        const profileData = `data:${customerProfileType};base64,${customerProfileBase64}`;
        if (customerProfileBase64) {
          setProfileImg(profileData);
        }
        setMessages(response.data);
      })
      .catch((error) => console.error("Failed to fetch chat messages.", error));
  };
  // 새 메시지를 보내는 함수
  const sendMessage = () => {
    if (stompClient.current && message) {
      const messageObj = {
        chatroomSeq: roomId,
        senderSeq: currentUser.userSeq,
        sender: currentUser.userId,
        message: message,
      };

      stompClient.current.send(`/pub/message`, {}, JSON.stringify(messageObj));
      setMessage(""); // 입력 필드 초기화
    }
  };
  return (
    <div>
      {/* 메시지 목록을 표시하는 부분 */}
      <div style={{ height: "300px", overflowY: "auto" }}>
        {messages.map((msg, index) => (
          <div
            key={index}
            style={{
              textAlign: msg.sender === currentUser.userId ? "right" : "left",
            }}
          >
            <img src={profileImg} />
            {msg.sender !== currentUser.userId && <p>{msg.sender}</p>}

            <p>{msg.message}</p>
          </div>
        ))}
        {/* 새 메시지가 추가될 때 스크롤을 이동시키는 요소 */}
        <div ref={messagesEndRef} />
      </div>
      {/* 메시지 입력 및 전송 부분 */}
      <div>
        <input
          type="text"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
        />
        <button onClick={sendMessage}>Send</button>
      </div>
      {currentUser.role === "HOSPITAL" && (
        <Reserve customerUserSeq={customerSeq} />
      )}
    </div>
  );
}
export default ChatApp;
