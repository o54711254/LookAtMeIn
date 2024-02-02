import React, { useEffect, useState } from 'react';
import axiosApi from '../../api/axiosApi';

const DoctorInfo = () => {
  const [doctorInfo, setDoctorInfo] = useState(null);
  const doctorSeq = '의사 번호'; // 의사 번호 입력

  useEffect(() => {
    const fetchDoctorInfo = async () => {
      try {
        const response = await axiosApi.get(`/doctor/list/${doctorSeq}`);
        const data = response.data;
        if (data.statusCode === '상태 코드' && data.message === 'success') {
          setDoctorInfo(data.responseObj);
        } else {
          console.error('의사 정보를 불러오지 못했습니다:', data.message);
        }
      } catch (error) {
        console.error('의사 정보를 불러오는 중 에러 발생:', error);
      }
    };

    fetchDoctorInfo();
  }, [doctorSeq]);

  return (
    <div>
      {doctorInfo && (
        <div>
          <h2>의사 정보</h2>
          <p>이름: {doctorInfo.doctor_name}</p>
          <p>카테고리: {doctorInfo.doctor_category}</p>
          <p>약력: {doctorInfo.history.history_content}</p>
        </div>
      )}
    </div>
  );
};

export default DoctorInfo;
