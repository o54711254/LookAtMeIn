import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import StarResult from "./StarRating/StarResult";
import { useNavigate } from "react-router-dom";
import styles from "./ReviewList.module.css";
import profile from "../../assets/gun.png";

// axios 완료d

function ReviewList() {
  const [reviewBoardList, setReviewBoardList] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    axiosApi.get(`/api/reviewBoard/list`).then((res) => {
      setReviewBoardList(res.data);
      console.log(reviewBoardList);
    });
  }, []);

  const handleClick = (reviewBoard_seq) => {
    navigate(`/reviewdetail/${reviewBoard_seq}`);
  };

  const goregist = () => {
    navigate("/reviewregist");
  }

  return (
    <div>
      <div className={styles.boardhead}>
        <div className={styles.headtitle}>
          <p>후기 게시판</p>
        </div>
        <div className={styles.headtext}>
          <p>룩앳미인에서 다양한 사람들의 후기를 확인하세요</p>
        </div>
      </div>
      <div>
        {reviewBoardList.map((board) => (
          <li
            key={board.reviewBoard_seq}
            onClick={() => handleClick(board.reviewBoard_seq)}
            className={styles.reviewItem}
          >
            <div>
              <img src={profile} alt="프로필" className={styles.profile} />
            </div>
            <div className={styles.writer}>
              <div>{board.customer_name}</div>
              <div>
                <StarResult score={board.reviewBoard_score} />
              </div>
            </div>
            <div className={styles.title}>
              <div>{board.reviewBoard_title}</div>
            </div>
            <div className={styles.price}>
              {/* 시술가 : {board.reviewBoard_price} 원 */}
            </div>
          </li>
        ))}
      </div>        
      <div>
      <button variant="contained" color="error" onClick={goregist}>
        후기 등록
      </button>
      </div>
    </div>
  );
}

export default ReviewList;
