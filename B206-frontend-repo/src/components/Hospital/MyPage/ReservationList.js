import React, { useState, useEffect } from "react";
import axiosApi from "../../../api/axiosApi";
import { useSelector } from "react-redux"; // useSelector를 불러옵니다.
import styles from "./ReservationList.module.css"
import { useNavigate } from "react-router-dom";
import profile from "../../../assets/profile2.png";


function ReservationList() {
  // Redux store에서 userId를 가져옵니다.
  const userSeq = useSelector((state) => state.user.userSeq);
  const navigate = useNavigate();
  const [reservations, setReservations] = useState([]);

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const response = await axiosApi.get(`/api/reserve/user/${userSeq}`); // 병원용으로 바꿔야함
        console.log(response.data);
        setReservations(response.data);
      } catch (error) {
        console.error("Error fetching reservations:", error);
      }
    };

    fetchReservations();
  }, [userSeq]);

  const goDetailPage = (reserveSeq) => {
    navigate(`/mypage/reserve/detail/${reserveSeq}`);
  };

  return (
    <div className={styles.reserveContainer}>
    {reservations.map((reservation) => (
      <div
        key={reservation.reserveSeq} // 고유 key 값을 추가했습니다.
        className={styles.reserveItem}
      >
        <div
          className={styles.forMove}
          onClick={() => goDetailPage(reservation.reserveSeq)}
        >
          <div className={styles.head}>
            <img src={profile} className={styles.profileImg} />
            <div className={styles.hospitalName}>
              {reservation.hospitalName}
            </div>
            <div>님과의 상담 예약</div>
          </div>
          <div className={styles.reserveContents}>
            <div className={styles.date}>
              <div>
                날짜 : {reservation.year}년
                {String(reservation.month).padStart(2, "0")}월
                {String(reservation.day).padStart(2, "0")}일
              </div>
              <div>요일 : {reservation.dayofweek}</div>
              <div>
                시간 : {reservation.time < 12
                  ? `${reservation.time}AM`
                  : `${
                      reservation.time === 12
                        ? reservation.time
                        : reservation.time - 12
                    }PM`}
              </div>
            </div>
          </div>
        </div>
      </div>
    ))}
  </div> // 이전에 누락된 닫는 괄호를 추가했습니다.
  );
}

export default ReservationList;
