import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axiosApi from "../../../api/axiosApi";
import { useParams } from 'react-router-dom';

function Doctor() {
  const [doctorList, setDoctorList] = useState([]);

  useEffect(() => {
    const fetchDoctorList = async () => {
      try {
        const response = await axiosApi.get(`/api/hospital/doctors`);
        const { statusCode, message, responseObj } = response.data;
        if (statusCode === '상태 코드' && message === 'success') {
          setDoctorList(responseObj);
        } else {
          console.error('의사 정보를 불러오는데 실패했습니다.');
        }
      } catch (error) {
        console.error('의사 정보를 불러오는데 실패했습니다:', error);
      }
    };

    fetchDoctorList();
  }, []);

  return (
    <div>
      <h2>의사 리스트</h2>
      <ul>
        {doctorList.map((doctor) => (
          <li key={doctor.id}>
            <Link to={`/list/${doctor.id}`}>{doctor.doctor_name}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
};
export default Doctor;
