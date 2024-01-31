import React, { useState, useEffect } from 'react';
import axiosApi from '../../api/axiosApi';

function ReviewBoardList() {
  const [reviewBoardList, setReviewBoardList] = useState([]);

  useEffect(() => {
    axiosApi
    .get(`/api/reviewBoard/list`)
    .then((res) => {
      setReviewBoardList(res.data.responseObj);
    });
}, []);

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
            <div>별점: {board.reviewBoard_score}</div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ReviewBoardList;
