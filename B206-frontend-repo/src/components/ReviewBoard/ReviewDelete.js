import React from "react";
import Button from "@mui/material/Button";
import axiosApi from "../../api/axiosApi";
import { useNavigate, useParams } from "react-router-dom";
import styles from "./ReviewDelete.module.css";

function ReviewDelete({ reviewBoard_seq, onUpdated }) {
  const navigate = useNavigate();
  const handleUpdate = () => {
    console.log(reviewBoard_seq);
    axiosApi
      .put(`/api/reviewBoard/delete/${reviewBoard_seq}`, {
        requestBoard_complain: true,
      })
      .then((response) => {
        console.log("Complain flag updated:", response.data);
        navigate("/reviewBoard/list");
        onUpdated(); // 상위 컴포넌트의 업데이트 처리 콜백
      })
      .catch((error) => {
        console.error("There was an error!", error);
      });
  };

  return <button className={styles.delete} onClick={handleUpdate}></button>;
}

export default ReviewDelete;
