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
  const user = useSelector((state) => state.user);
  const customer = useSelector((state) => state.customer);
  const [reviewImage, setReviewImage] = useState(""); // 리뷰 이미지 상태
  const [tag, setTag] = useState(""); // 현재 입력 중인 해시태그를 관리하는 상태
  const [tags, setTags] = useState([]); // 추가된 해시태그 목록을 관리하는 상태

  const [reviewData, setReviewData] = useState({
    user_seq: user.userSeq,
    reviewBoard_title: "",
    reviewBoard_content: "",
    reviewBoard_score: 0,
    username: customer.customerName,
    reviewBoard_doctor: "",
    reviewBoard_region: "",
    reviewBoard_surgery: "",
    reviewBoard_hospital: "",
    reviewBoard_expected_price: 0,
    reviewBoard_surgery_price: 0,
    hashtags: [],
  });

  // 입력 필드 변경 핸들러
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setReviewData({ ...reviewData, [name]: value });
  };

  // 해시태그 입력 변경 핸들러
  const handleTagChange = (e) => {
    setTag(e.target.value); // 입력 필드가 변경될 때마다 tag 상태 업데이트
  };

  // 해시태그 추가 핸들러
  const addTag = () => {
    if (tag && !tags.includes(tag)) {
      setTags([...tags, tag]); // 현재 입력된 태그를 태그 목록에 추가
      setTag(""); // 입력 필드 초기화
    }
  };

  // 이미지 변경 핸들러
  const handleImageChange = (e) => {
    if (e.target.files[0]) {
      setReviewImage(e.target.files[0]);
    }
  };

  // 폼 제출 핸들러
  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    const reviewBoardData = { ...reviewData, hashtags: tags };

    console.log("reviewBoardData", reviewBoardData);
    formData.append("reviewBoardData", JSON.stringify(reviewBoardData));

    if (reviewImage) {
      formData.append("uploadFile", reviewImage);
    }

    try {
      await axiosApi.post("/api/reviewBoard/regist", formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      navigate("/reviewBoard/list"); // 리뷰 목록 페이지로 이동
    } catch (error) {
      console.error("리뷰 등록 실패", error);
    }
  };

  return (
    <div>
      <h1>리뷰 등록</h1>
      <form onSubmit={handleSubmit}>
        {/* 입력 필드들 */}
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
          onChange={(value) =>
            setReviewData({ ...reviewData, reviewBoard_score: value })
          }
        />
        <TextField
          label="해시태그"
          value={tag}
          onChange={handleTagChange}
          fullWidth
          margin="normal"
        />
        <Button
          variant="contained"
          color="primary"
          onClick={addTag}
          style={{ margin: "10px" }}
        >
          해시태그 추가
        </Button>
        <div>
          {tags.map((tag, index) => (
            <span key={index} style={{ marginRight: "10px" }}>
              {tag}
            </span>
          ))}
        </div>
        <IconButton color="primary" component="label">
          <input
            hidden
            accept="image/*"
            type="file"
            onChange={handleImageChange}
          />
          <PhotoCamera />
        </IconButton>
        <Button
          type="submit"
          variant="contained"
          color="primary"
          style={{ marginTop: "20px" }}
        >
          등록하기
        </Button>
      </form>
    </div>
  );
}

export default ReviewRegist;
