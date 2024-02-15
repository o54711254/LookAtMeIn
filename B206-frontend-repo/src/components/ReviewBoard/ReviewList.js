import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import StarResult from "./StarRating/StarResult";
import { useNavigate } from "react-router-dom";
import styles from "./ReviewList.module.css";
import profile from "../../assets/profile2.png";
// import viewCnt from "../../assets/viewCnt.png";
import AOS from "aos";
import "aos/dist/aos.css";

// axios 완료d

function ReviewList() {
  const [reviewBoardList, setReviewBoardList] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    axiosApi.get(`/api/reviewBoard/list`).then((res) => {
      const updateData = res.data.map((board) => {
        if (board.customerProfileBase64 && board.customerProfileType) {
          board.img = `data:${board.customerProfileType};base64,${board.customerProfileBase64}`;
        } else {
          board.img = profile;
        }
        return board;
      });

      setReviewBoardList(updateData);
      console.log(updateData);
    });
  }, []);

  const handleClick = (reviewBoard_seq) => {
    navigate(`/reviewdetail/${reviewBoard_seq}`);
  };

  const goregist = () => {
    navigate("/reviewregist");
  };

  useEffect(() => {
    AOS.init({
      duration: 200,
    });
  });

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
            data-aos="fade-up"
          >
            <div>
              {/* <img src={profile} alt="프로필" className={styles.profile} /> */}
              {board.img && (
                <img
                  src={board.img}
                  alt="자게 작성자 프사"
                  className={styles.profile}
                />
              )}
            </div>
            <div className={styles.writer}>
              <div>{board.customer_name}</div>
              <div>
                <StarResult score={board.reviewBoard_score} />
              </div>
            </div>
            <div className={styles.titleCnt}>
              <div className={styles.title}>
                <div>{board.reviewBoard_title}</div>
              </div>
              <div className={styles.viewCount}>
                {/* <img src={viewCnt} alt="조회수 아이콘" className={styles.eye} /> */}
                조회수 : {board.reviewBoard_cnt}
              </div>
            </div>
            <div className={styles.price}>
              <div className={styles.p}>
                상담 견적가 : <em>{board.reviewBoard_expected_price}</em> 원
              </div>
              <div className={styles.p}>
                실제 시술가 :<em>{board.reviewBoard_surgery_price}</em> 원
              </div>
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
