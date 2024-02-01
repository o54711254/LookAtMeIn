import React from 'react';
import axios from 'axios';
import Button from '@mui/material/Button';

const ReviewDelete = ({ itemId, onDeleted }) => {
  const handleDelete = () => {
    axios.put(`/api/items/${itemId}/delete`)
      .then(response => {
        // 성공적으로 처리되었을 때의 로직
        console.log('Item deleted:', response.data);
        onDeleted(); // 상위 컴포넌트의 삭제 처리 콜백
      })
      .catch(error => {
        // 오류 처리 로직
        console.error('There was an error!', error);
      });
  };

  return (
    <Button variant="contained" color="error" onClick={handleDelete}>
      삭제하기
    </Button>
  );
};

export default ReviewDelete;
