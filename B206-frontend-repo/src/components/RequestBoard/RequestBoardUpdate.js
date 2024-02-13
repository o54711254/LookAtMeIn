import React, { useState } from "react";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import TextField from "@mui/material/TextField";
import { useNavigate } from "react-router-dom";
import styles from "./RequestBoardUpdate.module.css";

function RequestBoardUpdate({
  requestboardTitle,
  requestboardContent,
  requestboardSeq,
  requestboardPart, // surgeries의 part 정보를 배열로 받음
}) {
  const [open, setOpen] = useState(false);
  const user = useSelector((state) => state.user);
  const navigate = useNavigate();
  const [post, setPost] = useState({
    title: requestboardTitle,
    content: requestboardContent,
    surgeries: requestboardPart.map((part) => ({ part })), // surgeries를 객체 배열로 변환
  });

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleUpdate = async () => {
    try {
      const response = await axiosApi.put(
        `/api/requestboard/update/${requestboardSeq}`,
        post
      );
      console.log("글 수정 성공 : ", response.data);
      setOpen(false);
      window.location.reload();
    } catch (error) {
      console.log("게시글 수정 에러 : ", error);
    }
  };

  return (
    <>
      <button onClick={handleClickOpen} className={styles.button}></button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>게시물을 수정해주세요.</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="title"
            label="제목"
            type="text"
            fullWidth
            variant="outlined"
            value={post.title}
            onChange={(e) => setPost({ ...post, title: e.target.value })}
          />
          <TextField
            margin="dense"
            id="content"
            label="내용"
            type="text"
            fullWidth
            multiline
            rows={4}
            variant="outlined"
            value={post.content}
            onChange={(e) => setPost({ ...post, content: e.target.value })}
          />
          {/* surgeries의 part 정보를 입력받는 필드는 복잡도를 고려하여 생략함 */}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>취소</Button>
          <Button onClick={handleUpdate}>등록하기</Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
export default RequestBoardUpdate;
