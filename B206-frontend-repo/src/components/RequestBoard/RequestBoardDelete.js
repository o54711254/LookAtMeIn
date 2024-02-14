import React from "react";
import { useNavigate } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import styles from "./RequestBoardDelete.module.css";

const RequestBoardDelete = ({ requestBoardSeq }) => {
  const navigate = useNavigate();

  const handleUpdate = () => {
    axiosApi
      .put(`/api/requestboard/delete/${requestBoardSeq}`, {
        freeBoard_complain: true,
      })
      .then((response) => {
        console.log("자게 글 삭제됨 : ", response.data);
        navigate("/requestboard/requestBoardList");
      })
      .catch((error) => {
        console.error("There was an error!", error);
      });
  };

  return (
    <button
      variant="contained"
      onClick={handleUpdate}
      className={styles.button}
    ></button>
  );
};

export default RequestBoardDelete;
