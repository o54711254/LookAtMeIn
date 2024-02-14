import React, { useState, useEffect } from "react";
import axiosApi from "../../../api/axiosApi";
import { useSelector } from "react-redux";
import Questionnaire from "../../Modal/Questionnaire";
import { useNavigate } from "react-router-dom";
import ReservationDetail from "./ReservationDetail";
import styles from "./ReservationList.module.css";
import profile from "../../../assets/profile2.png";
// Axios 연결 확인 완료

function ReservationList() {
  const userSeq = useSelector((state) => state.user.userSeq);
  const [reservations, setReservations] = useState([]);
  const navigate = useNavigate();
  const [profileImg, setProfileImg] = useState(null);

  useEffect(() => {
    axiosApi
      .get(`api/reserve/user/${userSeq}`)
      .then((response) => {
        console.log(response.data);
        setReservations(response.data);

        const hospitalProfileBase64 = response.data.hospitalProfileBase64;
        const hospitalProfileType = response.data.hospitalProfileType;
        const profileData = `data:${hospitalProfileType};base64,${hospitalProfileBase64}`;
        if (hospitalProfileBase64) {
          setProfileImg(profileData);
        } else {
          setProfileImg(profile);
        }
      })
      .catch((error) => {
        console.error("고객 마이페이지 상담할 예약 내역 조회 에러 : ", error);
      });
  }, [userSeq]); // userSeq를 의존성 배열에 추가했습니다.

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
              <img src={profileImg} />
              <div className={styles.hospitalName}>
                {reservation.hospitalName}
              </div>
              <div>님과의 상담 예약</div>
            </div>
            <div className={styles.reserveContents}>
              <div className={styles.date}>
                <div>
                  날짜: {reservation.year}년
                  {String(reservation.month).padStart(2, "0")}월
                  {String(reservation.day).padStart(2, "0")}일
                </div>
                <div>요일 : {reservation.dayofweek}</div>
                <div>
                  시간 :
                  {reservation.time < 12
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
          {!reservation.questionnaireResponseDto && (
            <div className={styles.button}>
              <Questionnaire reserveSeq={reservation.reserveSeq} />
            </div>
          )}
        </div>
      ))}
    </div> // 이전에 누락된 닫는 괄호를 추가했습니다.
  );
}

export default ReservationList;
