import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

const HospitalDetail = (hospital_seq) => {
  const [hospitalData, setHospitalData] = useState({
    hospitalInfo_name: '',
    hospitalInfo_phoneNumber: '',
    hospitalInfo_introduce: '',
    hospitalInfo_addresss: '',
    hospitalInfo_email: '',
    hospitalInfo_open: '',
    hospitalInfo_favorite: '',
    hospitalInfo_url: '',
  });

  useEffect(() => {
    const getHospitalInfo = async () => {
      try {
        const response = await axios.get(`/api/hospital-info/detail/${hospital_seq}`);
        setHospitalData(response.data);
      } catch (error) {
        console.error('병원 정보를 가져오는데 실패했습니다:', error);
      }
    };

    getHospitalInfo();
  }, []);

  return (
    <Box sx={{ padding: 2 }}>
      <Typography variant="h5">{hospitalData.hospitalInfo_name}</Typography>
      <Typography variant="subtitle1">{hospitalData.hospitalInfo_phoneNumber}</Typography>
      <Typography variant="body1">{hospitalData.hospitalInfo_introduce}</Typography>
      <Typography variant="body1">{hospitalData.hospitalInfo_addresss}</Typography>
      <Typography variant="body1">{hospitalData.hospitalInfo_email}</Typography>
      <Typography variant="body1">{hospitalData.hospitalInfo_open}</Typography>
      <Typography variant="body1">{hospitalData.hospitalInfo_favorite}</Typography>
      <Typography variant="body1">{hospitalData.hospitalInfo_url}</Typography>
    </Box>
  );
};

export default HospitalDetail;

