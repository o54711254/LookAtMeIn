import React, { useEffect, useState } from "react";
import axiosAPi from "../../../../api/axiosApi";
import StarResult from "../../../ReviewBoard/StarRating/StarResult";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import styles from "./ReviewList.module.css";
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
        console.log("시술 후기 데이터를 가져오는데 실패했습니다.", error);
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
            <div
              className={styles.reviewContainer}
              onClick={() => handleClick(board.reviewBoard_seq)}
            >
              <div className={styles.front}>
                <div className={styles.writer}> {board.customer_name}</div>
                <StarResult score={board.reviewBoard_score} />
              </div>
              <div className={styles.content}>{board.reviewBoard_title}</div>
              <div className={styles.surgery}>#{board.reviewBoard_surgery}</div>
              <div> 병원 : {board.reviewBoard_hospital}</div>
              <div className={styles.region}> #{board.reviewBoard_region}</div>
              <div className={styles.price}>
                {" "}
                가격 : {board.reviewBoard_price}
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div>작성한 시술 후기가 없습니다.</div>
      )}
    </div>
  );
}
export default ReviewList;
