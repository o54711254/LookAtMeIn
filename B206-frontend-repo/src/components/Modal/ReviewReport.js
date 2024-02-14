import React, { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import axiosApi from "../../api/axiosApi";

function ReviewReportModal({ reviewBoard_seq }) {
  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const reviewReport = () => {
    // 올바르게 axios 호출 시작
    axiosApi.put(`/api/reviewBoard/report/${reviewBoard_seq}`)
      .then((response) => {
        // 성공적으로 리뷰 신고가 완료되었을 때 실행될 코드
        console.log('리뷰 신고 완료:', reviewBoard_seq);
  
        // 신고 완료 후 모달 닫기 또는 다른 UI 업데이트
        setOpen(false);
      })
      .catch((error) => {
        // 에러 처리
        console.error('리뷰 신고 중 에러 발생:', error);
      });
  };
  
  return (
    <div>
      <Button variant="outlined" onClick={handleClickOpen}>
        리뷰 신고하기
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>리뷰 신고</DialogTitle>
        <DialogContent>
          <DialogContentText>
            이 리뷰를 신고하시겠습니까?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>취소</Button>
          <Button onClick={reviewReport} autoFocus>
            신고하기
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default ReviewReportModal;
