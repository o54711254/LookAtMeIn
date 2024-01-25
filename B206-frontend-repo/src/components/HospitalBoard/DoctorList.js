import React, { useEffect, useState } from 'react';
import axiosApi from './axiosApi'; // axiosApi를 사용할 수 있는 경로에 따라 경로를 수정해야 합니다.

const DoctorList = () => {
  const [doctorList, setDoctorList] = useState([]);

  useEffect(() => {
    const fetchDoctorList = async () => {
      try {
        const response = await axiosApi.get('/doctor/list');
        const { statusCode, message, responseObj } = response.data;
        if (statusCode === '상태 코드' && message === 'success') {
          setDoctorList(responseObj);
        } else {
          console.error('의사 리스트를 불러오지 못했습니다:', message);
        }
      } catch (error) {
        console.error('의사 리스트를 불러오는 중 에러 발생:', error);
      }
    };

    fetchDoctorList();
  }, []);

  return (
    <div>
      <h2>의사 리스트</h2>
      <ul>
        {doctorList.map((doctor, index) => (
          <li key={index}>
            <p>이름: {doctor.doctor_name}</p>
            <p>카테고리: {doctor.doctor_category}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default DoctorList;
