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
  const [open, setOpen] = useState(false);
  const [image, setImage] = useState(null);
  const [doctor, setDoctor] = useState({
    doc_info_name: "",
    doc_info_category: [],
    doc_info_career: [
      {
        career_start: "",
        career_end: "",
        career_content: "",
      },
    ],
  });

  const hospital_seq = useSelector((state) => state.hospital.hospitalSeq);
  const [tempCategory, setTempCategory] = useState("");

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setImage(event.target.files[0]);
    }
  };

  const handleAddCategory = () => {
    setDoctor((prev) => ({
      ...prev,
      doc_info_category: [...prev.doc_info_category, { part: tempCategory }],
    }));
    setTempCategory("");
  };

  const handleRegist = async () => {
    const formData = new FormData();

    if (image) {
      formData.append("doctorProfile", image);
    }

    // Adjusting the structure of doctorData to match the backend expectation
    const doctorData = JSON.stringify({
      doc_info_name: doctor.doc_info_name,
      doc_info_category: doctor.doc_info_category,
      doc_info_career: doctor.doc_info_career,
    });

    formData.append("doctorData", doctorData);

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
      alert("의사가 성공적으로 등록되었습니다");
      setOpen(false); // Close the dialog

      // Reset form state
      setDoctor({
        doc_info_name: "",
        doc_info_category: [],
        doc_info_career: [
          {
            career_start: "",
            career_end: "",
            career_content: "",
          },
        ],
      });
      setImage(null);
      window.location.reload();
    } catch (error) {
      alert("의사 등록 실패");
      console.error("Registration error: ", error);
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
                label={category.part}
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
          {/* Dynamic fields for career information could be added here */}
          <TextField
            margin="dense"
            id="career_start"
            label="경력 시작 년도"
            type="text"
            fullWidth
            variant="outlined"
            value={doctor.doc_info_career[0].career_start}
            onChange={(e) =>
              setDoctor({
                ...doctor,
                doc_info_career: [
                  {
                    ...doctor.doc_info_career[0],
                    career_start: e.target.value,
                  },
                ],
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
            value={doctor.doc_info_career[0].career_end}
            onChange={(e) =>
              setDoctor({
                ...doctor,
                doc_info_career: [
                  { ...doctor.doc_info_career[0], career_end: e.target.value },
                ],
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
            value={doctor.doc_info_career[0].career_content}
            onChange={(e) =>
              setDoctor({
                ...doctor,
                doc_info_career: [
                  {
                    ...doctor.doc_info_career[0],
                    career_content: e.target.value,
                  },
                ],
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
          <Button onClick={handleRegist}>등록하기</Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default DoctorRegist;
