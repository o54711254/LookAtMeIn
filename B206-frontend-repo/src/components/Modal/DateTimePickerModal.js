import React, { useState } from 'react';
import Box from '@mui/joy/Box';
import Button from '@mui/joy/Button';
import Modal from '@mui/joy/Modal';
import ModalDialog from '@mui/joy/ModalDialog';
import Typography from '@mui/joy/Typography';
import TextField from '@mui/material/TextField';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { addDays, startOfDay } from 'date-fns';

export default function ResponsiveModal() {
  const [open, setOpen] = useState(false);
  const [date, setDate] = useState(startOfDay(addDays(new Date(), 1)));
  const [selectedTime, setSelectedTime] = useState(null);

  const times = Array.from({ length: 13 }, (_, index) => index + 9)
                     .filter(hour => hour !== 12) // 12시를 제외합니다.
                     .map(hour => `${hour}:00 ${hour >= 12 ? 'PM' : 'AM'}`);

  const handleTimeSelect = (time) => {
    setSelectedTime(time);
  };

  const disablePastDate = (current) => {
    const today = startOfDay(new Date());
    return current < today;
  };

  const handleConfirm = () => {
    console.log('Selected Date:', date);
    console.log('Selected Time:', selectedTime);
    setOpen(false);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <React.Fragment>
      <Button variant="outlined" color='primary' onClick={() => setOpen(true)}>
        예약하기
      </Button>
      <Modal id="modal" open={open} onClose={handleClose}>
        <ModalDialog
          aria-labelledby="nested-modal-title"
          aria-describedby="nested-modal-description"
          sx={{ width: "auto" }} // 모달 너비를 자동으로 설정합니다.
        >
          <Typography id="nested-modal-title" level="h2" sx={{ mb: 2 }}>
            날짜와 시간을 선택하세요.
          </Typography>
          <LocalizationProvider dateAdapter={AdapterDateFns}>
            <DatePicker
              label="Date"
              value={date}
              onChange={setDate}
              renderInput={(params) => <TextField {...params} />}
              shouldDisableDate={disablePastDate}
              sx={{ mb: 2 }}
            />
          </LocalizationProvider>
          <Box
            sx={{
              display: 'grid',
              gridTemplateColumns: 'repeat(6, 1fr)', // 한 줄에 6개의 버튼이 들어가도록 설정합니다.
              gap: 2,
              mb: 2,
            }}
          >
            {times.map((time) => (
              <Button
                key={time}
                variant={selectedTime === time ? 'solid' : 'outlined'}
                color="neutral"
                onClick={() => handleTimeSelect(time)}
              >
                {time}
              </Button>
            ))}
          </Box>
          <Box
            sx={{
              display: 'flex',
              justifyContent: 'flex-end',
              gap: 2,
            }}
          >
            <Button variant="outlined" color="danger" onClick={handleClose}>
              취소
            </Button>
            <Button variant="solid" color="success" onClick={handleConfirm}>
              예약하기
            </Button>
          </Box>
        </ModalDialog>
      </Modal>
    </React.Fragment>
  );
}
