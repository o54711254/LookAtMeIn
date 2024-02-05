import React, { useState, useEffect, useMemo } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Button, TextField } from "@mui/material";
import StarInput from "./StarRating/StarRating";
import axios from "axios";

function ReviewUpdate() {
  const location = useLocation();
  const initialReviewDetail = useMemo(() => {
    return location.state || {};
  }, [location.state]);
  const [updateData, setUpdateData] = useState(initialReviewDetail);
  const navigate = useNavigate();

  useEffect(() => {
    console.log(initialReviewDetail);
    setUpdateData(initialReviewDetail);
  }, [initialReviewDetail]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdateData({ ...updateData, [name]: value });
  };
  const handleRatingChange = (value) => {
    setUpdateData({ ...updateData, reviewBoard_score: value });
  };

  const handleSubmit = () => {
    axios
      .put(`/api/reviewBoard/update`, updateData)
      .then((res) => {
        console.log(res.data);
        navigate(`/reviewdetail/${updateData.reviewBoard_seq}`);
      })
      .catch((error) => {
        console.log("수정 중 에러 발생", error);
      });
  };

  return (
    <div>
      <TextField
        label="제목"
        name="reviewBoard_title"
        value={updateData.reviewBoard_title || ""}
        onChange={handleChange}
      />
      <TextField
        label="내용"
        name="reviewBoard_content"
        value={updateData.reviewBoard_content || ""}
        onChange={handleChange}
        multiline
        rows={4}
      />
      <StarInput
        name="reviewBoard_score"
        value={updateData.reviewBoard_score}
        onChange={handleRatingChange}
      />
      <Button variant="contained" color="error" onClick={handleSubmit}>
        수정
      </Button>
    </div>
  );
}

export default ReviewUpdate;
