// ReviewBoardForm.js
import React, { useState, useEffect } from 'react';
import axiosApi from '../../api/axiosApi';
import { useSelector } from "react-redux";
<<<<<<< HEAD
import StarInput from './StarRating/StarInput';

function ReviewBoardForm({ onReviewAdded, consultingSeq }) {

    const userSeq = useSelector((store) => store.user.userSeq);
=======

function ReviewBoardForm({ onReviewAdded, consultingSeq }) {

    const customerSeq = useSelector((store) => store.custormer.customerSeq);
>>>>>>> dev-FE-jinry0034
    const [consulting, setConsulting] = useState([]);

    useEffect(() => {
        axiosApi
<<<<<<< HEAD
        .get(`/api/customer/${userSeq}/consulting/list/${consultingSeq}`)
=======
        .get(`/api/customer/${customerSeq}/consulting/list/${consultingSeq}`)
>>>>>>> dev-FE-jinry0034
        .then((res) => {
          setConsulting(res.data.responseObj);
        });
    }, []);

<<<<<<< HEAD
  // 지역, 의사, 병원 추가 필요 & reviewBoard 에서 가격 표시하려면 dto 추가 필요  
=======
>>>>>>> dev-FE-jinry0034
  const [reviewData, setReviewData] = useState({
    reviewBoard_title: '',
    reviewBoard_content: '',
    reviewBoard_score: '',
    customer_id: '',
    reviewBoard_doctor: '',
    reviewBoard_region: '',
    reviewBoard_surgery: consulting.part,
    reviewBoard_hospital: '',
  });
<<<<<<< HEAD
  
=======
>>>>>>> dev-FE-jinry0034

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setReviewData({ ...reviewData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axiosApi.post('/api/reviewBoard', reviewData)
      .then((res) => {
        onReviewAdded(res.data); // 새로운 리뷰가 등록되면 부모 컴포넌트에서 처리할 수 있도록 콜백 호출
      })
      .catch((error) => {
        console.error('Error adding review:', error);
      });
  };

<<<<<<< HEAD
  const handleRatingChange = (value) => {
    setReviewData({ ...reviewData, reviewBoard_score: value });
  };

=======
>>>>>>> dev-FE-jinry0034
  return (
    <div>
      <h1>리뷰 등록</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="reviewBoard_title">제목:</label>
          <input type="text" id="reviewBoard_title" name="reviewBoard_title" value={reviewData.reviewBoard_title} onChange={handleInputChange} />
        </div>
        <div>
          <label htmlFor="reviewBoard_content">내용:</label>
          <textarea id="reviewBoard_content" name="reviewBoard_content" value={reviewData.reviewBoard_content} onChange={handleInputChange} />
        </div>
        <div>
<<<<<<< HEAD
        <label htmlFor="reviewBoard_score">별점:</label>
          {/* 별점 입력 컴포넌트를 렌더링합니다. */}
          <StarInput value={reviewData.reviewBoard_score} onChange={handleRatingChange} />
=======
          <label htmlFor="reviewBoard_score">별점:</label>
          <input type="number" id="reviewBoard_score" name="reviewBoard_score" value={reviewData.reviewBoard_score} onChange={handleInputChange} />
>>>>>>> dev-FE-jinry0034
        </div>
        <button type="submit">등록</button>
      </form>
    </div>
  );
}

export default ReviewBoardForm;
