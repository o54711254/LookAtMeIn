import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import StarRating from './StarRating/StarResult';

function ReviewList() {
  // const [reviewBoardList, setReviewBoardList] = useState([]);

  // useEffect(() => {
  //   axiosApi.get(`/api/reviewBoard/list`).then((res) => {
  //     setReviewBoardList(res.data.responseObj);
  //   });
  // }, []);

  // 더미 데이터
  const reviewBoardList = [{
    customer_id: 'user123',
    reviewBoard_title: '테스트 리뷰',
    reviewBoard_cnt: 100,
    reviewBoard_regDate: '2024-01-28',
    reviewBoard_score: 4,
  }]

  return (
    <div>
      <h1>리뷰 보드 목록</h1>
      <ul>
        {reviewBoardList.map((board) => (
          <li key={board.customer_id}>
            <div>작성자: {board.customer_id}</div>
            <div>제목: {board.reviewBoard_title}</div>
            <div>조회수: {board.reviewBoard_cnt}</div>
            <div>작성날짜: {board.reviewBoard_regDate}</div>
            <div><StarRating score={board.reviewBoard_score}/></div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ReviewList;
