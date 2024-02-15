import React, { useState } from "react";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import Button from "@mui/material/Button";
import TextareaAutosize from "@mui/material/TextareaAutosize";
import Typography from "@mui/material/Typography";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

function SuggestModal({ isOpen, onClose, onSubmit }) {
  const [message, setMessage] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(message);
    onClose();
  };

  return (
    <Modal
      open={isOpen}
      onClose={onClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <Typography id="modal-modal-title" variant="h6" component="h2">
          제안하기
        </Typography>
        <TextareaAutosize
          aria-label="제안 메시지"
          minRows={3}
          placeholder="제안 메시지를 작성하세요"
          style={{ width: "100%", marginTop: "16px", marginBottom: "16px" }}
          value={message}
          onChange={(e) => setMessage(e.target.value)}
        />
        <Box textAlign="right">
          <Button onClick={onClose} style={{ marginRight: "8px" }}>
            닫기
          </Button>
          <Button variant="contained" onClick={handleSubmit}>
            제안하기
          </Button>
        </Box>
      </Box>
    </Modal>
  );
}

export default SuggestModal;
