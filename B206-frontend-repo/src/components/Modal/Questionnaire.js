import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import IconButton from '@mui/material/IconButton';
import PhotoCamera from '@mui/icons-material/PhotoCamera';

export default function FormDialog() {
  const [open, setOpen] = React.useState(false);
  const [image, setImage] = React.useState(null);
  const [questionnaire, setQuestionnaire] = React.useState({
    questionnaire_remark: "",
    questionnaire_blood: "",
    questionnaire_content: "",
    questionnaire_image: image,
  });

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleRegist = () => {
    
  }

  const handleImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setImage(URL.createObjectURL(event.target.files[0]));
    }
  };

  return (
    <div>
      <Button variant="outlined" onClick={handleClickOpen}>
        문진표 등록
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>상담받고 싶은 부위를 작성해주세요.</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="reason"
            label="글 내용"
            type="text"
            fullWidth
            variant="standard"
          />
          <TextField
            margin="dense"
            id="description"
            label="상세 내용"
            type="text"
            fullWidth
            multiline
            rows={4}
            variant="standard"
          />
          <IconButton color="primary" aria-label="upload picture" component="label">
            <input hidden accept="image/*" type="file" onChange={handleImageChange} />
            <PhotoCamera />
          </IconButton>
          {image && <img src={image} alt="Preview" style={{ maxWidth: '100px', maxHeight: '100px' }} />}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>취소</Button>
          <Button onClick={handleClose}>등록하기</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
