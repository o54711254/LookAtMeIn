import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import TextField from "@mui/material/TextField";
import IconButton from "@mui/material/IconButton";
import PhotoCamera from "@mui/icons-material/PhotoCamera";
import { useNavigate } from "react-router-dom";
import styles from "./FreeBoardUpdate.module.css";

function FreeBoardUpdate({ freeboardTitle, freeboardContent, freeboardSeq }) {
  const [open, setOpen] = React.useState(false); // 모달의 열림/닫힘 상태를 관리하는 state입니다. 처음에는 false로 모달이 닫혀있는 상태입니다.
  const [image, setImage] = React.useState(null); // 사용자가 업로드한 이미지 파일을 저장할 state입니다. 초기값은 null입니다.

  const user = useSelector((state) => state.user);
  const navigate = useNavigate();
  const [post, setPost] = React.useState({
    freeboardTitle: freeboardTitle,
    freeboardContent: freeboardContent,
    user_seq: user.userSeq,
  });

  const handleClickOpen = () => {
    // 모달을 열기 위한 함수
    setOpen(true);
  };

  const handleClose = () => {
    // 모달을 닫기 위한 함수
    setOpen(false);
  };

  //등록 버튼을 클릭했을 때 실행되는 함수
  const handleRegist = async () => {
    const formData = new FormData();
    if (image) {
      formData.append("image", image); //이미지파일 있으면 FormData에 추가
    }
    Object.keys(post).forEach((key) => {
      formData.append(key, post[key]);
    });

    try {
      const response = await axiosApi.put(
        `/api/freeBoard/update/${freeboardSeq}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      console.log("글 작성 성공 : ", response.data);
      setOpen(false); //다이얼로그 닫기

      //폼 상태 초기화
      setPost({
        freeBoard_title: "",
        freeBoard_content: "",
      });
      setImage(null);
      window.location.reload();
    } catch (error) {
      console.log(post);
      console.log(formData);
      console.log("자게 글 작성 에러남 : ", error);
    }
  };

  // 이미지 파일을 선택했을 때 실행되는 함수
  const handleImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setImage(event.target.files[0]); // 선택된 파일을 image state에 저장
    }
  };
  return (
    <>
      <button
        variant="outlined"
        onClick={handleClickOpen}
        className={styles.button}
      ></button>
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
            value={post.freeBoard_title}
            onChange={(e) =>
              setPost({ ...post, freeBoard_title: e.target.value })
            }
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
            value={post.freeBoard_content}
            onChange={(e) =>
              setPost({ ...post, freeBoard_content: e.target.value })
            }
          />
          <IconButton
            color="primary"
            aria-label="upload picture"
            component="label"
          >
            <input
              hidden
              accept="image/*"
              type="file"
              onChange={handleImageChange}
            />
            <PhotoCamera />
          </IconButton>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>취소</Button>
          <Button onClick={handleRegist}>등록하기</Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
export default FreeBoardUpdate;
