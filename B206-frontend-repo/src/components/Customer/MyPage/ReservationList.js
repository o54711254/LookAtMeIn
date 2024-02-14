import React, { useState, useEffect } from "react";
import axiosApi from "../../../api/axiosApi"; // axiosApi 모듈을 불러옵니다.
import { useSelector } from "react-redux"; // useSelector를 불러옵니다.
import Questionnaire from "../../Modal/Questionnaire";
import { Route, Routes, useNavigate } from "react-router-dom";
import ReservationDetail from "./ReservationDetail";
import styles from "./ReservationList.module.css";
// Axios 연결 확인 완료

function ReservationList() {
  // Redux store에서 userId를 가져옵니다.
  const userSeq = useSelector((state) => state.user.userSeq);
  const [reservations, setReservations] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    axiosApi
      .get(`api/reserve/user/${userSeq}`)
      .then((response) => {
        console.log(response.data);
        setReservations(response.data);
      })
      .catch((error) => {
        console.log("고객 마이페이지 상담할 예약 내역 조회 에러 : ", error);
      });
  }, []);

  const goDetailPage = (reserveSeq) => {
    navigate(`/mypage/reserve/detail/${reserveSeq}`);
  };
  
  return (
    <div className={styles.reserveContainer}>
      {reservations.map((reservation) => (
        <div
          onClick={() => goDetailPage(reservation.reserveSeq)}
          className={styles.reserveItem}
        >
          {/* <p>Customer Name: {reservation.customerName}</p> */}
          <div className={styles.head}>
            <div className={styles.hospitalName}>
              {reservation.hospitalName}
            </div>
            <div>님과의 상담 예약</div>
          </div>
          <div className={styles.contents}>
            <div className={styles.date}>
              <div>
                상담 예약 날짜: {reservation.year}년
                {String(reservation.month).padStart(2, "0")}월
                {String(reservation.day).padStart(2, "0")}일
              </div>
              <div>{reservation.dayofweek}</div>
              <div>
                {" "}
                {reservation.time < 12
                  ? `${reservation.time} AM`
                  : `${
                      reservation.time === 12
                        ? reservation.time
                        : reservation.time - 12
                    } PM`}
              </div>
            </div>
            <div className={styles.button}>
              <Questionnaire />
            </div>
          </div>
        </div>
      ))}

      <Routes>
        <Route path="detail/:reserveSeq" element={<ReservationDetail />} />
      </Routes>
    </div>
  );
}

export default ReservationList;
