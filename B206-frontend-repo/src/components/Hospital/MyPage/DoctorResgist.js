// ReviewBoardForm.js
import React, { useState, useEffect } from "react";
import axiosApi from "../../../api/axiosApi";
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

function DoctorRegist() {
  const [open, setOpen] = useState(false); // 모달의 열림/닫힘 상태를 관리하는 state입니다. 처음에는 false로 모달이 닫혀있는 상태입니다.
  const [image, setImage] = useState(null); // 사용자가 업로드한 이미지 파일을 저장할 state입니다. 초기값은 null입니다.

  // const user = useSelector((state) => state.user);
  const hospital_seq = useSelector((state) => state.hospital.hospitalSeq);
  const navigate = useNavigate();
  const [doctor, setDoctor] = useState({
    doc_info_name: "",
    doc_info_category: "",
    hospital_seq: hospital_seq,
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
      formData.append("doctorProfile", image); //이미지파일 있으면 FormData에 추가
    }
    // Object.keys(doctor).forEach((key) => {
    //   formData.append(key, doctor[key]);
    // });
    formData.append(
      "doctorData",
      JSON.stringify({
        doc_info_name: doctor.doc_info_name,
        doc_info_category: doctor.doc_info_category,
      })
    );
    for (let [key, value] of formData.entries()) {
      console.log(`${key}: ${value}`);
    }

    try {
      const response = await axiosApi.post(
        `/api/hospital/${hospital_seq}/doctors/regist`,
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
      setDoctor({
        doc_info_name: "",
        doc_info_category: "",
      });
      setImage(null);
      window.location.reload();
    } catch (error) {
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
      <Button variant="outlined" onClick={handleClickOpen}>
        의사 등록하기
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>의사를 등록해주세요</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="이름"
            type="text"
            fullWidth
            variant="outlined"
            value={doctor.doc_info_name}
            onChange={(e) =>
              setDoctor({ ...doctor, doc_info_name: e.target.value })
            }
          />
          <TextField
            margin="dense"
            id="category"
            label="전문분야"
            type="text"
            fullWidth
            multiline
            rows={4}
            variant="outlined"
            value={doctor.doc_info_category}
            onChange={(e) =>
              setDoctor({ ...doctor, doc_info_category: e.target.value })
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

export default DoctorRegist;
