import React, { useState } from "react";
import { Box, Button, Paper } from "@mui/material";
import Draggable from "react-draggable";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom"; // Link 컴포넌트 추가

function FloatingChat() {
  const [open, setOpen] = useState(false);
  const [chatRooms, setChatRooms] = useState([]); // 채팅방 목록 상태
  const user = useSelector((store) => store.user.userName);

  const fakeChatRooms = [
    { id: 1, name: "채팅방 1" },
    { id: 2, name: "채팅방 2" },
    // ... 기타 채팅방
  ];

  const handleToggleChat = () => {
    setOpen(!open);
    if (!open) {
      fetchChatRooms(); // 채팅 목록을 가져오는 함수
    }
  };

  const fetchChatRooms = async () => {
    setChatRooms(fakeChatRooms);
  };

  // const fetchChatRooms = async () => {
  //   try {
  //     const res = await axiosApi.get(`/chat/rooms/${user}`);
  //     setChatRooms(res.data);
  //   } catch (error) {
  //     console.log("채팅방 목록을 가져오는데 실패했습니다.", error);
  //   }
  // };

  return (
    <Draggable>
      <Paper style={{ position: "fixed", bottom: 10, right: 10, zIndex: 1000 }}>
        <Button onClick={handleToggleChat}>{open ? "숨기기" : "채팅"}</Button>
        {open && (
          <Box sx={{ p: 10, maxWidth: 300 }}>
            {/* ChatRoom 컴포넌트의 기능을 여기에 직접 포함시킴 */}
            <div>
              {chatRooms.map((room) => (
                <Link key={room.id} to={"/chat-app/" + room.id}>
                  {room.name}
                </Link>
              ))}
            </div>
          </Box>
        )}
      </Paper>
    </Draggable>
  );
}

export default FloatingChat;
