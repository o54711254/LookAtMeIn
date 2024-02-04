// ReviewBoardForm.js
import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";
import StarRating from "./StarRating/StarRating";

function ReviewRegist({ onReviewAdded, consultingSeq }) {
  const userSeq = useSelector((store) => store.user.userSeq);
  const [consulting, setConsulting] = useState([]);

  useEffect(() => {
    axiosApi
      .get(`/api/customer/${userSeq}/consulting/list/${consultingSeq}`)
      .then((res) => {
        setConsulting(res.data);
      })
      .catch((error) => {
        console.log("데이터를 불러오는데 실패했습니다.", error);
      });
  }, []);

  // 지역, 의사, 병원 추가 필요 & reviewBoard 에서 가격 표시하려면 dto 추가 필요
  const [reviewData, setReviewData] = useState({
    user_seq: userSeq,
    reviewBoard_title: "string",
    reviewBoard_content: "string",
    reviewBoard_score: 0,
    username: "string",
    reviewBoard_doctor: "string",
    reviewBoard_region: "string",
    reviewBoard_surgery: "string",
    reviewBoard_hospital: "string",
    reviewBoard_price: 0,
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setReviewData({ ...reviewData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axiosApi
      .post("/api/reviewBoard/regist", reviewData)
      .then((res) => {
        onReviewAdded(res.data); // 새로운 리뷰가 등록되면 부모 컴포넌트에서 처리할 수 있도록 콜백 호출
      })
      .catch((error) => {
        console.error("Error adding review:", error);
      });
  };

  const handleRatingChange = (value) => {
    setReviewData({ ...reviewData, reviewBoard_score: value });
  };

  return (
    <div>
      <h1>리뷰 등록</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="reviewBoard_title">제목:</label>
          <input
            type="text"
            id="reviewBoard_title"
            name="reviewBoard_title"
            value={reviewData.reviewBoard_title}
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label htmlFor="reviewBoard_content">내용:</label>
          <textarea
            id="reviewBoard_content"
            name="reviewBoard_content"
            value={reviewData.reviewBoard_content}
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label htmlFor="reviewBoard_price">가격:</label>
          <input
            type="number"
            id="reviewBoard_price"
            name="reviewBoard_price"
            value={reviewData.reviewBoard_price}
            onChange={handleInputChange}
          />
        </div>

        <div>
          <label htmlFor="reviewBoard_score">별점:</label>
          {/* 별점 입력 컴포넌트를 렌더링합니다. */}
          <StarRating
            value={reviewData.reviewBoard_score}
            onChange={handleRatingChange}
          />
        </div>
        <button type="submit">등록</button>
      </form>
    </div>
  );
}

export default ReviewRegist;
