import * as React from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import TextField from "@mui/material/TextField";
import IconButton from "@mui/material/IconButton";
import PhotoCamera from "@mui/icons-material/PhotoCamera";
import ToggleButton from "@mui/material/ToggleButton";
import ToggleButtonGroup from "@mui/material/ToggleButtonGroup";
import axiosApi from "../../api/axiosApi"; // AxiosApi 임포트
import styles from "./Questionnaire.module.css";

export default function FormDialog({ reserveSeq }) {
  // console.log(props.reserveSeq)
  const [open, setOpen] = React.useState(false);
  const [image, setImage] = React.useState(null);
  const [questionnaire, setQuestionnaire] = React.useState({
    questionnaire_remark: "",
    questionnaire_blood: "",
    questionnaire_content: "",
  });

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleRegist = async () => {
    const formData = new FormData();

    // const
    console.log("questionnaire: ", questionnaire);
    const questionnaireData = {};
    questionnaireData["reserveSeq"] = reserveSeq;

    // 기존 questionnaire 데이터를 FormData에 추가
    Object.keys(questionnaire).forEach((key) => {
      // formData.append(key, questionnaire[key]);
      questionnaireData[key] = questionnaire[key];
    });

    console.log("questionnaireData: ", questionnaireData);
    formData.append("questionnaireData", JSON.stringify(questionnaireData));

    if (image) {
      formData.append("image", image); // 서버에서 사용하는 필드명('image' 등)으로 교체 가능
    }

    try {
      console.log("문진표데이터", questionnaireData);
      const response = await axiosApi.post(
        "/api/questionnaire/regist",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      console.log("success: ", response.data);

      setOpen(false);
      setOpen(false); // 다이얼로그 닫기
      // 폼 상태 초기화
      setQuestionnaire({
        questionnaire_remark: "",
        questionnaire_blood: "",
        questionnaire_content: "",
      });
      setImage(null);
    } catch (e) {
      console.log("error: ", e);
    }

    // axiosApi.post('/api/questionnaire/regist', formData, {
    //   headers: {
    //     'Content-Type': 'multipart/form-data',
    //   },
    // })
    // .then(response => {
    //   console.log('Success:', response.data);
    //   // 성공 처리 로직
    //   setOpen(false); // 다이얼로그 닫기
    //   // 폼 상태 초기화
    //   setQuestionnaire({
    //     questionnaire_remark: "",
    //     questionnaire_blood: "",
    //     questionnaire_content: "",
    //   });
    //   setImage(null);
    // })
    // .catch(error => {
    //   console.error('Error:', error);
    //   // 오류 처리 로직
    // });
  };

  const handleImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setImage(event.target.files[0]);
    }
  };

  return (
    <div className={styles.buttonContainer}>
      <Button
        variant="outlined"
        onClick={handleClickOpen}
        className={styles.button}
      >
        문진표
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>상담받고 싶은 부위를 작성해주세요.</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="reason"
            label="글 내용"
            type="text"
            fullWidth
            variant="standard"
            value={questionnaire.questionnaire_remark}
            onChange={(e) =>
              setQuestionnaire({
                ...questionnaire,
                questionnaire_remark: e.target.value,
              })
            }
          />
          <TextField
            margin="dense"
            id="description"
            label="상세 내용"
            type="text"
            fullWidth
            multiline
            rows={4}
            variant="standard"
            value={questionnaire.questionnaire_content}
            onChange={(e) =>
              setQuestionnaire({
                ...questionnaire,
                questionnaire_content: e.target.value,
              })
            }
          />
          <ToggleButtonGroup
            color="primary"
            value={questionnaire.questionnaire_blood}
            exclusive
            onChange={(event, newBloodType) => {
              if (newBloodType !== null) {
                setQuestionnaire({
                  ...questionnaire,
                  questionnaire_blood: newBloodType,
                });
              }
            }}
            aria-label="blood type"
          >
            <ToggleButton value="A">A형</ToggleButton>
            <ToggleButton value="B">B형</ToggleButton>
            <ToggleButton value="AB">AB형</ToggleButton>
            <ToggleButton value="O">O형</ToggleButton>
          </ToggleButtonGroup>
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
          <Button onClick={handleClose}>취소</Button>
          <Button onClick={handleRegist}>등록하기</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
