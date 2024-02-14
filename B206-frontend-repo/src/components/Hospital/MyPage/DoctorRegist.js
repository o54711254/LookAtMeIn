// ReviewBoardForm.js
import React, { useState } from "react";
import axiosApi from "../../../api/axiosApi";
import { useSelector } from "react-redux";
import Button from "@mui/material/Button";
import { Chip, Stack } from "@mui/material";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import TextField from "@mui/material/TextField";
import IconButton from "@mui/material/IconButton";
import PhotoCamera from "@mui/icons-material/PhotoCamera";

function DoctorRegist() {
  const [open, setOpen] = useState(false); // 모달의 열림/닫힘 상태를 관리하는 state입니다. 처음에는 false로 모달이 닫혀있는 상태입니다.
  const [image, setImage] = useState(null); // 사용자가 업로드한 이미지 파일을 저장할 state입니다. 초기값은 null입니다.
  const [doctor, setDoctor] = useState({
    doc_info_name: "",
    doc_info_category: [],
    doc_info_career: {
      career_start: "",
      career_end: "",
      career_content: "",
    },
  });

  const hospital_seq = useSelector((state) => state.hospital.hospitalSeq);
  const [tempCategory, setTempCategory] = useState("");
  const handleClickOpen = () => {
    // 모달을 열기 위한 함수
    setOpen(true);
  };

  const handleClose = () => {
    // 모달을 닫기 위한 함수
    setOpen(false);
  };

  // 이미지 파일을 선택했을 때 실행되는 함수
  const handleImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setImage(event.target.files[0]); // 선택된 파일을 image state에 저장
    }
  };

  const handleAddCategory = () => {
    setDoctor((prev) => ({
      ...prev,
      doc_info_category: [...prev.doc_info_category, tempCategory],
    }));
    setTempCategory("");
  };

  //등록 버튼을 클릭했을 때 실행되는 함수
  const handleRegist = async () => {
    const formData = new FormData();

    if (image) {
      formData.append("uploadFile", image); //이미지파일 있으면 FormData에 추가
    }
    formData.append("doc_info_name", doctor.doc_info_name);

    doctor.doc_info_category.forEach((category, index) => {
      formData.append(
        `doc_info_category[${index}]`,
        JSON.stringify({ part: category })
      );
    });
    formData.append("doc_info_career", JSON.stringify(doctor.doc_info_career));
    // Object.keys(doctor).forEach((key) => {
    //   formData.append(key, doctor[key]);
    // });

    for (let [key, value] of formData.entries()) {
      console.log(`${key}: ${value}`);
    }

    try {
      const response = await axiosApi.post(
        `api/hospital/${hospital_seq}/doctors/regist`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      alert("의사가 성공적으로 등록되었습니다");
      console.log("의사 등록 성공 : ", response.data);
      setOpen(false); //다이얼로그 닫기

      //폼 상태 초기화
      setDoctor({
        doc_info_name: "",
        doc_info_category: [],
        doc_info_career: {
          career_start: "",
          career_end: "",
          career_content: "",
        },
      });
      setImage(null);
      window.location.reload();
    } catch (error) {
      console.log("의사 등록 에러남 : ", error);
      alert("의사 등록 실패");
    }
  };

  return (
    <>
      <Button variant="outlined" onClick={handleClickOpen}>
        의사 등록하기
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>의사 정보 입력</DialogTitle>
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
            label="전문 분야"
            type="text"
            fullWidth
            variant="outlined"
            value={tempCategory}
            onChange={(e) => setTempCategory(e.target.value)}
          />
          <Button onClick={handleAddCategory} style={{ marginTop: "10px" }}>
            분야 추가하기
          </Button>
          <Stack direction="row" spacing={1} mt={1}>
            {doctor.doc_info_category.map((category, index) => (
              <Chip
                key={index}
                label={category}
                onDelete={() => {
                  setDoctor((prev) => ({
                    ...prev,
                    doc_info_category: prev.doc_info_category.filter(
                      (_, i) => i !== index
                    ),
                  }));
                }}
              />
            ))}
          </Stack>
          {/* 경력 정보 입력 필드 */}
          <TextField
            margin="dense"
            id="career_start"
            label="경력 시작 년도"
            type="text"
            fullWidth
            variant="outlined"
            value={doctor.doc_info_career.career_start}
            onChange={(e) =>
              setDoctor({
                ...doctor,
                doc_info_career: {
                  ...doctor.doc_info_career,
                  career_start: e.target.value,
                },
              })
            }
          />
          <TextField
            margin="dense"
            id="career_end"
            label="경력 종료 년도"
            type="text"
            fullWidth
            variant="outlined"
            value={doctor.doc_info_career.career_end}
            onChange={(e) =>
              setDoctor({
                ...doctor,
                doc_info_career: {
                  ...doctor.doc_info_career,
                  career_end: e.target.value,
                },
              })
            }
          />
          <TextField
            margin="dense"
            id="career_content"
            label="경력 내용"
            type="text"
            fullWidth
            multiline
            rows={4}
            variant="outlined"
            value={doctor.doc_info_career.career_content}
            onChange={(e) =>
              setDoctor({
                ...doctor,
                doc_info_career: {
                  ...doctor.doc_info_career,
                  career_content: e.target.value,
                },
              })
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
          {image && (
            <img
              src={URL.createObjectURL(image)}
              alt="Preview"
              style={{ maxWidth: "100px", maxHeight: "100px" }}
            />
          )}
        </DialogContent>
        <DialogActions>
          {/* <Button onClick={handleClose}>취소</Button> */}
          <Button onClick={handleRegist}>등록하기</Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default DoctorRegist;
