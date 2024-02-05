import React from "react";
import axios from "axios";
import Button from "@mui/material/Button";
import { useNavigate } from "react-router-dom";

const FreeBoardDelete = ({ freeBoardSeq }) => {
  const navigate = useNavigate();

  const handleUpdate = () => {
    axios
      .put(`/api/freeBoard/delete/${freeBoardSeq}`, {
        freeBoard_complain: true,
      })
      .then((response) => {
        console.log("자게 글 삭제됨 : ", response.data);
        navigate("/freeBoard/freeBoardList");
      })
      .catch((error) => {
        console.error("There was an error!", error);
      });
  };

  return (
    <Button variant="contained" onClick={handleUpdate}>
      삭제하기
    </Button>
  );
};

export default FreeBoardDelete;
