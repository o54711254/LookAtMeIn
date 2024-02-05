import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import StarResult from "./StarRating/StarResult";
import ReviewDelete from "./ReviewDelete";

// axios 완료 (reviewBoard_seq 넘어오는 것만 확인하면 될 듯)

function ReviewDetail() {
  const [reviewDetail, setReviewDetail] = useState([]);
  const { reviewBoard_seq } = useParams(); // URL 파라미터에서 reviewBoard_seq를 가져옴
  const navigate = useNavigate();

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

  const onReviewDeleted = () => {
    navigate(`/reviewList`);
  };

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
        <ReviewDelete
          reviewBoard_seq={reviewBoard_seq}
          onUpdated={onReviewDeleted}
        ></ReviewDelete>
      </div>
    </div>
  );
}

export default ReviewDetail;
