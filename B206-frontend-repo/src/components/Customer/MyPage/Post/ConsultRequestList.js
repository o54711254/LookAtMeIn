import React, { useEffect, useState } from "react";
import axiosApi from "../../../../api/axiosApi";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import profile from "../../../../assets/gun.png";
import styles from "./ReviewList.module.css";
import AOS from "aos";
import "aos/dist/aos.css";

// axios 완료
function ConsultRequestList() {
  const [requestBoardList, setRequestBoardList] = useState([]);
  const user = useSelector((state) => state.user);
  const navigate = useNavigate();

  useEffect(() => {
    axiosApi
      .get(`/api/mypage/request/${user.userSeq}`)
      .then((res) => {
        console.log(res.data);
        setRequestBoardList(res.data);
        console(requestBoardList);
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
    navigate(`/requestBoard/requestBoardList/${reviewBoard_seq}`);
  };

  return (
    <div>
      {requestBoardList.length >= 0 ? (
        <div>
          {requestBoardList.map((board) => (
            <div
              key={board.requestBoard_seq}
              onClick={() => handleClick(board.requestBoard_seq)}
              className={styles.requestItem}
              data-aos="fade-up"
            >
              <div>
                <img src={profile} alt="프로필" className={styles.profile} />
              </div>
              <div className={styles.writer}>
                <div>{board.customer_name}</div>
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
export default ConsultRequestList;
