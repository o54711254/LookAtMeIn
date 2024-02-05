import React, { useState, useEffect } from "react";
import axiosApi from "../../../api/axiosApi"; // axiosApi 모듈을 불러옵니다.
import { useSelector } from "react-redux"; // useSelector를 불러옵니다.

// Axios 연결 확인 완료

function ReservationList() {
  // Redux store에서 userId를 가져옵니다.
  const userSeq = useSelector((state) => state.user.userSeq);

  const [reservations, setReservations] = useState([]);

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const response = await axiosApi.get(`/api/reserve/user/${userSeq}`); // axiosApi를 사용하여 HTTP GET 요청을 보냅니다.
        console.log(response.data);
        setReservations(response.data);
      } catch (error) {
        console.error("Error fetching reservations:", error);
      }
    };

    fetchReservations();
  }, [userSeq]);

  return (
    <div>
      <h2>Reservation List</h2>
      <ul>
        {reservations.map((reservation, index) => (
          <li key={index}>
            <p>reserveSeq: {reservation.reserveSeq}</p>
            <p>reserveTime: {reservation.reserveTime}</p>
            {/* <p>
              Date: {reservation.year}-{reservation.month}-{reservation.day}
            </p>
            <p>Day of Week: {reservation.dayofweek}</p>
            <p>Time: {reservation.time}</p> */}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ReservationList;
