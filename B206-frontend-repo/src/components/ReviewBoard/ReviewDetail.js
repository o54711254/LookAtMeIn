import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";

function ReviewBoardList() {
  const [reviewDetail, setReviewDetail] = useState([]);

  useEffect(() => {
    axiosApi
      .get(`/api/reviewBoard/${reviewDetail.reviewBoard_seq}`)
      .then((res) => {
        setReviewDetail(res.data.responseObj);
      });
  }, []);

  return (
    <div>
      <h1>리뷰 보드 디테일</h1>
      <div>
        <div>제목: {reviewDetail.reviewBoard_title}</div>
        <div>내용: {reviewDetail.reviewBoard_content}</div>
        <div>별점: {reviewDetail.reviewBoard_score}</div>
        <div>작성자: {reviewDetail.customer_id}</div>
        <div>의사: {reviewDetail.reviewBoard_doctor}</div>
        <div>지역: {reviewDetail.reviewBoard_region}</div>
        <div>시술 부위: {reviewDetail.reviewBoard_surgery}</div>
        <div>병원: {reviewDetail.reviewBoard_hospital}</div>
      </div>
    </div>
  );
}

export default ReviewBoardList;
