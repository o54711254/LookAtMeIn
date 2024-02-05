import React from 'react';
import axios from 'axios';
import Button from '@mui/material/Button';

const ReviewBoardDelete = ({ reviewBoard_seq, onUpdated }) => {
  const handleUpdate = () => {
    axios.put(`/reviewBoard/update/${reviewBoard_seq}`, {
      requestBoard_complain: true
    })
    .then(response => {
      console.log('Complain flag updated:', response.data);
      onUpdated(); // 상위 컴포넌트의 업데이트 처리 콜백
    })
    .catch(error => {
      console.error('There was an error!', error);
    });
  };

  return (
    <Button variant="contained" color="error" onClick={handleUpdate}>
      신고하기
    </Button>
  );
};

export default ReviewBoardDelete;
