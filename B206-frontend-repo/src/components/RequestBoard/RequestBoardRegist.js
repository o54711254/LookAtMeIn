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
import styles from "./RequestBoardRegist.module.css";

function RequestBoardRegist() {
  const [open, setOpen] = useState(false);
  const user = useSelector((state) => state.user);
  const navigate = useNavigate();

  const [post, setPost] = useState({
    title: "",
    content: "",
    userSeq: user.userSeq,
    surgeries: [{ part: "" }], // 수술 유형 정보를 제거하고, 수술 부위만 남김
  });

  const handleClickOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleRegist = async () => {
    try {
      console.log(post);
      const response = await axiosApi.post(`/api/requestboard/register`, post);
      console.log("글 작성 성공 : ", response.data);
      handleClose();
      window.location.reload();
    } catch (error) {
      console.log("글 작성 에러 : ", error);
    }
  };

  const handleInputChange = (e, field) => {
    setPost({ ...post, [field]: e.target.value });
  };

  const handleSurgeryChange = (e, index) => {
    const newSurgeries = post.surgeries.map((surgery, idx) => {
      if (idx === index) {
        return { ...surgery, part: e.target.value };
      }
      return surgery;
    });
    setPost({ ...post, surgeries: newSurgeries });
  };

  return (
    <div className={styles.registContainer}>
      <Button
        variant="outlined"
        onClick={handleClickOpen}
        className={styles.button}
      >
        게시글 등록
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>게시물을 작성해주세요.</DialogTitle>
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
            onChange={(e) => handleInputChange(e, "title")}
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
            onChange={(e) => handleInputChange(e, "content")}
          />
          {post.surgeries.map((surgery, index) => (
            <div key={index}>
              <TextField
                margin="dense"
                id={`part-${index}`}
                label="수술 부위"
                type="text"
                fullWidth
                variant="outlined"
                value={surgery.part}
                onChange={(e) => handleSurgeryChange(e, index)}
              />
            </div>
          ))}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>취소</Button>
          <Button onClick={handleRegist}>등록하기</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default RequestBoardRegist;
