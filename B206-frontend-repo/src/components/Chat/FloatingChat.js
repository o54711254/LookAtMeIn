import React, { useState } from "react";
import {
  Box,
  TextField,
  Button,
  List,
  ListItem,
  Typography,
  Paper,
} from "@mui/material";
import Draggable from "react-draggable";

function FloatingChat() {
  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);

  const handleToggleChat = () => setOpen(!open);
  const handleSend = () => {
    setMessages([...messages, message]);
    setMessage("");
  };

  return (
    <Draggable>
      <Paper style={{ position: "fixed", bottom: 10, right: 10, zIndex: 1000 }}>
        <Button onClick={handleToggleChat}>{open ? "Hide" : "채팅창"}</Button>
        {open && (
          <Box sx={{ p: 2, maxWidth: 300 }}>
            <Typography variant="h6">Chat</Typography>
            <List sx={{ maxHeight: 200, overflow: "auto" }}>
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
        )}
      </Paper>
    </Draggable>
  );
}

export default FloatingChat;
