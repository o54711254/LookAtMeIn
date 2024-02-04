import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import StarResult from "./StarRating/StarResult";

function ReviewDetail() {
  const [reviewDetail, setReviewDetail] = useState([]);
  const { reviewBoard_seq } = useParams(); // URL 파라미터에서 reviewBoard_seq를 가져옴

  useEffect(() => {
    console.log(reviewBoard_seq);
    axiosApi
      .get(`/api/reviewBoard/${reviewBoard_seq}`)
      .then((res) => {
        console.log(res.data);
        setReviewDetail(res.data);
      })
      .catch((error) => {
        console.log("데이터를 불러오는데 실패했습니다.", error);
      });
  }, []);

  return (
    <div>
      <h1>리뷰 보드 디테일</h1>
      <div>
        <div>제목: {reviewDetail.reviewBoard_title}</div>
        <div>내용: {reviewDetail.reviewBoard_content}</div>
        <StarResult score={reviewDetail.reviewBoard_score} />
        <div>작성자: {reviewDetail.customer_id}</div>
        <div>의사: {reviewDetail.reviewBoard_doctor}</div>
        <div>지역: {reviewDetail.reviewBoard_region}</div>
        <div>시술 부위: {reviewDetail.reviewBoard_surgery}</div>
        <div>병원: {reviewDetail.reviewBoard_hospital}</div>
      </div>
    </div>
  );
}

export default ReviewDetail;
