import React, { useEffect, useState } from "react";
import axiosAPi from "../../../../api/axiosApi";
import StarResult from "../../../ReviewBoard/StarRating/StarResult";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
// axios 완료
function ReviewList() {
  const [reviewBoardList, setReviewBoardList] = useState([]);
  const user = useSelector((state) => state.user);
  const navigate = useNavigate();

  useEffect(() => {
    axiosAPi
      .get(`/api/mypage/review/${user.userSeq}`)
      .then((res) => {
        console.log(res.data);
        setReviewBoardList(res.data);
        console(reviewBoardList);
      })
      .catch((error) => {
        console.log("데이터를 가져오는데 실패했습니다.", error);
      });
  }, []);

  const handleClick = (reviewBoard_seq) => {
    navigate(`/reviewdetail/${reviewBoard_seq}`);
  };

  return (
    <div>
      <div>작성한 글 갯수 : {reviewBoardList.length}</div>
      {reviewBoardList.length >= 0 ? (
        <div>
          {reviewBoardList.map((board) => (
            <li
              key={board.reviewBoard_seq}
              onClick={() => handleClick(board.reviewBoard_seq)}
            >
              <div>작성자: {board.customer_name}</div>
              <div>제목: {board.reviewBoard_title}</div>
              <div>작성날짜: {board.reviewBoard_regDate}</div>
              <div>
                <StarResult score={board.reviewBoard_score} />
              </div>
            </li>
          ))}
        </div>
      ) : (
        <div>작성한 시술 후기가 없습니다.</div>
      )}
    </div>
  );
}
export default ReviewList;
