import React, { useState, useEffect } from "react";
import axiosApi from "../../../api/axiosApi"; // axiosApi 모듈을 불러옵니다.
import { useSelector } from "react-redux"; // useSelector를 불러옵니다.
import Questionnaire from "../../Modal/Questionnaire";
import { Route, Routes, useNavigate } from "react-router-dom";
import ReservationDetail from "./ReservationDetail";
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
    <div>
      <h2>Reservation List</h2>
      <div>
        {reservations.map((reservation) => (
          <div onClick={() => goDetailPage(reservation.reserveSeq)}>
            {/* <p>Customer Name: {reservation.customerName}</p> */}
            <p>Hospital Name: {reservation.hospitalName}</p>

            <p>
              상담 예약 날짜: {reservation.year}-
              {String(reservation.month).padStart(2, "0")}-
              {String(reservation.day).padStart(2, "0")}
            </p>
            <p>상담 예약 요일: {reservation.dayofweek}</p>
            <p>
              상담 예약 시간:{" "}
              {reservation.time < 12
                ? `${reservation.time} AM`
                : `${
                    reservation.time === 12
                      ? reservation.time
                      : reservation.time - 12
                  } PM`}
            </p>
            <Questionnaire reserveSeq={reservation.reserveSeq}/>
            <button>이동</button>
          </div>
        ))}
      </div>

      <Routes>
        <Route path="detail/:reserveSeq" element={<ReservationDetail />} />
      </Routes>
    </div>
  );
}

export default ReservationList;
