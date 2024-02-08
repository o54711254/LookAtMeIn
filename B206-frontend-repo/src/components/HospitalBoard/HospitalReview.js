import React, { useEffect } from "react";
import { Link } from "react-router-dom";
import { useState } from "react";
import styles from "./HospitalReview.module.css";
import { useNavigate } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import profile from "../../assets/gun.png";
import StarResult from "./StarRating/StarResult";

function HospitalReview() {
  const navigate = useNavigate();
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    axiosApi
      .get(`api/hospital-info/reviews/{hospital_seq}`)
      .then((response) => {
        console.log(response.data);
        setReviews(response.data);
      });
  }, []);

  const handleClick = (reviewBoard_seq) => {
    navigate(`api/reviewBoard/{seq}`);
  };

  return (
    <>
      <h3>병원별 리뷰</h3>
      {reviews.map((review) => (
        <li
          key={review.reviewBoard_seq}
          className={styles.reviewItem}
          onClick={() => handleClick(review.reviewBoard_seq)}
        >
          <div>
            <img src={profile} alt="프로필" className={styles.profile} />
          </div>
          <div className={styles.writer}>
            <div>{review.customer_name}</div>
            <div>
              <StarResult score={review.reviewBoard_score} />
            </div>
          </div>
          <div className={styles.title}>
            <div>{review.reviewBoard_title}</div>
          </div>
          <div className={styles.price}>
            시술가 : {review.reviewBoard_price} 원
          </div>
        </li>
      ))}
    </>
  );
}
export default HospitalReview;
