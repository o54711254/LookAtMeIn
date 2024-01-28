import React, { useState, useEffect } from 'react';
import axiosApi from './axiosApi'; // axiosApi 모듈을 불러옵니다.
import { useSelector } from 'react-redux'; // useSelector를 불러옵니다.

const ReservationList = () => {
  // Redux store에서 userId를 가져옵니다.
  const userId = useSelector(state => state.user.userId);

  const [reservations, setReservations] = useState([]);

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const response = await axiosApi.get(`/reservation/hospital/${userId}`); // axiosApi를 사용하여 HTTP GET 요청을 보냅니다.
        setReservations(response.data.responseObj.reserveRtc);
      } catch (error) {
        console.error('Error fetching reservations:', error);
      }
    };

    fetchReservations();
  }, [userId]);

  return (
    <div>
      <h2>Reservation List</h2>
      <ul>
        {reservations.map((reservation, index) => (
          <li key={index}>
            <p>Customer ID: {reservation.customerId}</p>
            <p>Coordinator ID: {reservation.coordinatorId}</p>
            <p>Date: {reservation.year}-{reservation.month}-{reservation.day}</p>
            <p>Day of Week: {reservation.dayofweek}</p>
            <p>Time: {reservation.time}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ReservationList;
