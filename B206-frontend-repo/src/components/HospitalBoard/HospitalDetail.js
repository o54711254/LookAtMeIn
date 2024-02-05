import React, { useState, useEffect } from 'react';
import axiosApi from '../../api/axiosApi';
import { useParams } from "react-router-dom";
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Reserve from '../Modal/DateTimePickerModal'
import { useDispatch } from "react-redux"
import { setHospital } from "../../redux/hospital.js";

const HospitalDetail = () => {
  const dispatch = useDispatch();
  const { hospital_seq } = useParams();
  const [hospitalData, setHospitalData] = useState({
    hospitalInfo_seq: '',
    hospitalInfo_name: '',
    hospitalInfo_phoneNumber: '',
    hospitalInfo_introduce: '',
    hospitalInfo_address: '',
    hospitalInfo_open: '',
    hospitalInfo_close:'',
    hospitalInfo_url: '',
    avgScore: '',
    cntReviews: ''
  });

  useEffect(() => {
    const getHospitalInfo = async () => {
      try {
        const response = await axiosApi
        .get(`/api/hospital-info/detail/${hospital_seq}`);
        setHospitalData(response.data);
        console.log(response.data);

        dispatch(
          setHospital({
            hospitalSeq: parseInt(hospital_seq, 10),
            hospitalName: response.data.hospitalInfo_name
          })
        );
      
      } catch (error) {
        console.error('병원 정보를 가져오는데 실패했습니다:', error);
      }
    };

    getHospitalInfo();
  }, []);

  return (
    <Box sx={{ padding: 2 }}>
       <div>
      <Typography variant="h5">Hospital Information</Typography>
      <Typography>hospitalInfo_seq: {hospitalData.hospitalInfo_seq}</Typography>
      <Typography>hospitalInfo_name: {hospitalData.hospitalInfo_name}</Typography>
      <Typography>hospitalInfo_phoneNumber: {hospitalData.hospitalInfo_phoneNumber}</Typography>
      <Typography>hospitalInfo_introduce: {hospitalData.hospitalInfo_introduce}</Typography>
      <Typography>hospitalInfo_address: {hospitalData.hospitalInfo_address}</Typography>
      <Typography>hospitalInfo_open: {hospitalData.hospitalInfo_open}</Typography>
      <Typography>hospitalInfo_close: {hospitalData.hospitalInfo_close}</Typography>
      <Typography>hospitalInfo_url: {hospitalData.hospitalInfo_url}</Typography>
      <Typography>avgScore: {hospitalData.avgScore}</Typography>
      <Typography>cntReviews: {hospitalData.cntReviews}</Typography>
    </div>
    <Reserve/>
    </Box>
  );
};

export default HospitalDetail;

