import React from "react";
import StarResult from "../../ReviewBoard/StarRating/StarResult";
import { useNavigate } from "react-router-dom";
import styles from "./ReviewBoardSearch.module.css";
import profile from "../../../assets/gun.png";

function ReviewList({ results }) {
  const navigate = useNavigate();

  const handleClick = (reviewBoard_seq) => {
    navigate(`/reviewdetail/${reviewBoard_seq}`);
  };

  return (
    <div>
      <div>
        {results.map((board) => (
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
              시술가 : {board.reviewBoard_price} 원
            </div>
          </li>
        ))}
      </div>
    </div>
  );
}

export default ReviewList;
