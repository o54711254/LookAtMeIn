import React, { useState } from "react";
import { Box, Button, Paper } from "@mui/material";
import Draggable from "react-draggable";
import ChatRoom from "./ChatRoom"; // 채팅방 목록 컴포넌트를 import합니다

function FloatingChat() {
  const [open, setOpen] = useState(false);
  const [showChatRooms, setShowChatRooms] = useState(false); // 채팅방 목록 표시 상태

  const handleToggleChat = () => {
    setOpen(!open);
    setShowChatRooms(false); // 채팅방 목록 숨기기
  };

  const handleShowChatRooms = () => {
    setShowChatRooms(true); // 채팅방 목록 표시
  };

  return (
    <Draggable>
      <Paper style={{ position: "fixed", bottom: 10, right: 10, zIndex: 1000 }}>
        <Button onClick={handleToggleChat}>{open ? "Hide" : "Chat"}</Button>
        {open && (
          <Box sx={{ p: 2, maxWidth: 300 }}>
            {!showChatRooms ? (
              // 채팅 입력 부분
              <div>
                {/* 여기에 채팅 입력과 관련된 컴포넌트 또는 요소를 추가하세요 */}
                <Button onClick={handleShowChatRooms}>Chat Rooms</Button>
              </div>
            ) : (
              // 채팅방 목록 부분
              <ChatRoom />
            )}
          </Box>
        )}
      </Paper>
    </Draggable>
  );
}

export default FloatingChat;
