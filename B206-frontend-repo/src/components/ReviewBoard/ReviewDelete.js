import React from 'react';
import axios from 'axios';
import Button from '@mui/material/Button';

const DeleteButton = ({ reviewBoard_seq, onDeleted }) => {
  const handleDelete = () => {
    axios.put(`/reviewBoard/delete/${reviewBoard_seq}`)
      .then(response => {
        console.log('Item deleted:', response.data);
        onDeleted(); // 상위 컴포넌트의 삭제 처리 콜백
      })
      .catch(error => {
        console.error('There was an error!', error);
      });
  };

  return (
    <Button variant="contained" color="error" onClick={handleDelete}>
      삭제하기
    </Button>
  );
};

export default DeleteButton;
