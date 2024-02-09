import { useEffect, useState } from "react";
import axiosApi from "../../../../api/axiosApi";
import StarResult from "../../../ReviewBoard/StarRating/StarResult";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import profile from "../../../../assets/gun.png";
import AOS from "aos";
import styles from "./ReportedReview.module.css";

function ReportedReview() {
  const [reviewBoardList, setReviewBoardList] = useState([]);
  const user = useSelector((state) => state.user);
  const navigate = useNavigate();

  useEffect(() => {
    axiosApi
      .get(`/api/admin/reviewComplain`)
      .then((res) => {
        console.log(res.data);
        setReviewBoardList(res.data);
        console(reviewBoardList);
      })
      .catch((error) => {
        console.log("시술 후기 데이터를 가져오는데 실패했습니다.", error);
      });
  }, []);

  useEffect(() => {
    AOS.init({
      duration: 200,
    });
  });

  const handleClick = (reviewBoard_seq) => {
    navigate(`/reviewdetail/${reviewBoard_seq}`);
  };

  return (
    <div>
      {reviewBoardList.length >= 0 ? (
        <div>
          {reviewBoardList.map((board) => (
            <div
              key={board.reviewBoard_seq}
              onClick={() => handleClick(board.reviewBoard_seq)}
              className={styles.reviewItem}
              data-aos="fade-up"
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
            </div>
          ))}
        </div>
      ) : (
        <div>작성한 시술 후기가 없습니다.</div>
      )}
    </div>
  );
}
export default ReportedReview;
