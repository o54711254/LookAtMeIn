// FloatingChat.js
import React, { useState } from "react";
import { Box, Button, Paper } from "@mui/material";
import Draggable from "react-draggable";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";
import { Link, Routes, Route, BrowserRouter } from "react-router-dom";
import ChatApp from "./ChatApp";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCommentDots,
  faCommentSlash,
} from "@fortawesome/free-solid-svg-icons";
import styles from "./FloatingChat.module.css"; // CSS 모듈 임포트
import chat from "../../assets/chat.png";

function FloatingChat() {
  const [open, setOpen] = useState(false);
  const [chatRooms, setChatRooms] = useState([]);
  const user = useSelector((state) => state.user);

  const handleToggleChat = () => {
    setOpen(!open);
    if (!open) {
      fetchChatRooms();
    }
  };

  const fetchChatRooms = async () => {
    try {
      const res = await axiosApi.get(`/api/chatrooms/${user.userSeq}`);
      console.log("res.data", res.data);
      setChatRooms(res.data);
    } catch (error) {
      console.log("채팅방 목록을 가져오는데 실패했습니다.", error);
    }
  };

  return (
    <BrowserRouter>
      <Draggable>
        <Paper className={styles.floatingChatContainer}>
          <button
            onClick={handleToggleChat}
            className={styles.toggleChatButton}
          ></button>
          {open && (
            <Box className={`${styles.chatBox} ${styles.chatLayout}`}>
              <div className={styles.chatList}>
                <div className={styles.title}>유저목록</div>
                {chatRooms.map((room) => (
                  <Link
                    key={room.chatRoomSeq}
                    to={`/chat-app/${room.chatRoomSeq}`}
                    className={styles.chatLink}
                  >
                    {room.chatRoomName}
                  </Link>
                ))}
              </div>
              <div className={styles.chatContent}>
                <Routes>
                  <Route path="chat-app/:roomId" element={<ChatApp />} />
                </Routes>
              </div>
            </Box>
          )}
        </Paper>
      </Draggable>
    </BrowserRouter>
  );
}

export default FloatingChat;
