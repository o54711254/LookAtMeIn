import React, { useState } from "react";
import {
  Modal,
  Box,
  TextField,
  Button,
  List,
  ListItem,
  Typography,
} from "@mui/material";

function ChatModal() {
  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const handleSend = () => {
    setMessages([...messages, message]);
    setMessage("");
  };

  const modalStyle = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: 400,
    bgcolor: "background.paper",
    boxShadow: 24,
    p: 4,
    outline: "none",
  };

  return (
    <div>
      <Button onClick={handleOpen}>Open Chat</Button>
      <Modal open={open} onClose={handleClose}>
        <Box sx={modalStyle}>
          <Typography variant="h6" component="h2">
            Chat
          </Typography>
          <List sx={{ maxHeight: 300, overflow: "auto" }}>
            {messages.map((msg, index) => (
              <ListItem key={index}>{msg}</ListItem>
            ))}
          </List>
          <Box
            component="form"
            sx={{ display: "flex", alignItems: "center", mt: 1 }}
          >
            <TextField
              fullWidth
              size="small"
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              placeholder="Type a message"
            />
            <Button onClick={handleSend}>Send</Button>
          </Box>
        </Box>
      </Modal>
    </div>
  );
}

export default ChatModal;
