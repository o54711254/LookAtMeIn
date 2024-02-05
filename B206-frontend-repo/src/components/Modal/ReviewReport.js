import React, { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

function ReviewReportModal({ reviewBoard_seq }) {
  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const reviewReport = () => {
    // 여기에 신고 처리 로직을 추가하세요. 예시로 console.log를 사용합니다.
    console.log('리뷰 신고 완료:', reviewBoard_seq);
    // 신고 완료 후 모달 닫기
    setOpen(false);
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
