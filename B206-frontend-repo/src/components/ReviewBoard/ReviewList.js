import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import StarRating from "./StarRating/StarResult";
import { useNavigate } from "react-router-dom";

function ReviewList() {
  const [reviewBoardList, setReviewBoardList] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    axiosApi.get(`/api/reviewBoard/list`).then((res) => {
      setReviewBoardList(res.data);
    });
  }, []);

  const handleClick = (reviewBoard_seq) => {
    navigate(`/reviewdetail/${reviewBoard_seq}`);
  };

  return (
    <div>
      <h1>리뷰 보드 목록</h1>
      <div>
        {reviewBoardList.map((board) => (
          <li
            key={board.reviewBoard_seq}
            onClick={() => handleClick(board.reviewBoard_seq)}
          >
            <div>작성자: {board.customer_name}</div>
            <div>제목: {board.reviewBoard_title}</div>
            <div>조회수: {board.reviewBoard_cnt}</div>
            <div>작성날짜: {board.reviewBoard_regDate}</div>
            <div>
              <StarRating score={board.reviewBoard_score} />
            </div>
          </li>
        ))}
      </div>
    </div>
  );
}

export default ReviewList;
