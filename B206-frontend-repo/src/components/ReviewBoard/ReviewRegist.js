import React, { useState } from "react";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";
import StarRating from "./StarRating/StarRating";
import { useNavigate } from "react-router-dom";
import IconButton from "@mui/material/IconButton";
import PhotoCamera from "@mui/icons-material/PhotoCamera";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";

function ReviewRegist() {
  const navigate = useNavigate();
  const userSeq = useSelector((store) => store.user.userSeq); // 사용자 ID 추출
  const userName = useSelector((store) => store.user.userName); // 사용자 ID 추출
  const [reviewImage, setReviewImage] = useState(null); // 리뷰 이미지 상태

  // 리뷰 데이터 상태 초기화
  const [reviewData, setReviewData] = useState({
    user_seq: userSeq,
    reviewBoard_title: "",
    reviewBoard_content: "",
    reviewBoard_score: 0,
    username: userName,
    reviewBoard_doctor: "",
    reviewBoard_region: "",
    reviewBoard_surgery: "",
    reviewBoard_hospital: "",
    reviewBoard_expected_price: 0,
    reviewBoard_surgery_price: 0,
  });

  // 입력 필드 변경 핸들러
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setReviewData({ ...reviewData, [name]: value });
  };

  // 이미지 변경 핸들러
  const handleImageChange = (e) => {
    if (e.target.files[0]) {
      setReviewImage(e.target.files[0]);
    }
  };

  // 폼 제출 핸들러
  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData();

    // reviewData의 키와 값을 FormData에 추가
    Object.keys(reviewData).forEach((key) => {
      formData.append(key, reviewData[key]);
    });

    // 이미지 파일 추가
    if (reviewImage) {
      formData.append("uploadfile", reviewImage);
    }

    // API 호출
    axiosApi.post("/api/reviewBoard/regist", formData, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then((res) => {
      console.log("리뷰 등록 성공", res.data);
      navigate("/reviewList"); // 성공 후 리뷰 목록 페이지로 이동
    })
    .catch((error) => {
      console.error("리뷰 등록 실패", error);
    });
  };

  return (
    <div>
      <h1>리뷰 등록</h1>
      <form onSubmit={handleSubmit}>
        <TextField
          label="제목"
          name="reviewBoard_title"
          value={reviewData.reviewBoard_title}
          onChange={handleInputChange}
          fullWidth
          margin="normal"
        />
        <TextField
          label="내용"
          name="reviewBoard_content"
          value={reviewData.reviewBoard_content}
          onChange={handleInputChange}
          fullWidth
          multiline
          rows={4}
          margin="normal"
        />
        <TextField
          label="의사 이름"
          name="reviewBoard_doctor"
          value={reviewData.reviewBoard_doctor}
          onChange={handleInputChange}
          fullWidth
        />
        <TextField
          label="지역"
          name="reviewBoard_region"
          value={reviewData.reviewBoard_region}
          onChange={handleInputChange}
          fullWidth
        />
        <TextField
          label="수술명"
          name="reviewBoard_surgery"
          value={reviewData.reviewBoard_surgery}
          onChange={handleInputChange}
          fullWidth
        />
        <TextField
          label="병원명"
          name="reviewBoard_hospital"
          value={reviewData.reviewBoard_hospital}
          onChange={handleInputChange}
          fullWidth
        />
        <TextField
          label="예상 가격"
          name="reviewBoard_expected_price"
          type="number"
          value={reviewData.reviewBoard_expected_price}
          onChange={handleInputChange}
          fullWidth
        />
        <TextField
          label="수술 가격"
          name="reviewBoard_surgery_price"
          type="number"
          value={reviewData.reviewBoard_surgery_price}
          onChange={handleInputChange}
          fullWidth
        />
        <StarRating
          value={reviewData.reviewBoard_score}
          onChange={(value) => setReviewData({ ...reviewData, reviewBoard_score: value })}
        />
        <IconButton color="primary" component="label">
          <input hidden accept="image/*" type="file" onChange={handleImageChange} />
          <PhotoCamera />
        </IconButton>
        <Button type="submit" variant="contained" color="primary">등록하기</Button>
      </form>
    </div>
  );
}

export default ReviewRegist;
