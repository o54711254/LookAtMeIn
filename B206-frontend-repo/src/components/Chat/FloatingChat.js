import React, { useState } from "react";
import { Box, Button, Paper } from "@mui/material";
import Draggable from "react-draggable";
import ChatRoom from "./ChatRoom";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";

function FloatingChat() {
  const [open, setOpen] = useState(false);
  const [chatRooms, setChatRooms] = useState([]); // 채팅방 목록 상태
  const user = useSelector((store) => store.user.userName);

  const handleToggleChat = () => {
    setOpen(!open);
    if (!open) {
      fetchChatRooms(); // 채팅 목록을 가져오는 함수
    }
  };

  const fetchChatRooms = () => {
    axiosApi
      .get(`/chat/rooms/${user}`) // 임의로
      .then((res) => {
        setChatRooms(res.data);
      })
      .catch((error) => {
        console.log("채팅방 목록을 가져오는데 실패했습니다.", error);
      });
  };

  return (
    <Draggable>
      <Paper style={{ position: "fixed", bottom: 10, right: 10, zIndex: 1000 }}>
        <Button onClick={handleToggleChat}>{open ? "숨기기" : "채팅"}</Button>
        {open && (
          <Box sx={{ p: 10, maxWidth: 300 }}>
            <ChatRoom chatRooms={chatRooms} />
          </Box>
        )}
      </Paper>
    </Draggable>
  );
}

export default FloatingChat;
