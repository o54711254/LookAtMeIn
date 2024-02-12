import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder"; // 빈 하트 아이콘
import FavoriteIcon from "@mui/icons-material/Favorite"; // 채워진 하트 아이콘
import IconButton from "@mui/material/IconButton";
import { useSelector } from "react-redux";

const FavoriteButton = () => {
  const [isFavorited, setIsFavorited] = useState(false);
  const userSeq = useSelector((state) => state.user.userSeq);
  const hospitalSeq = useSelector((state) => state.hospital.hospitalSeq);


  // useEffect(() => {
  //   // 병원이 이미 찜되었는지 확인하는 API 요청
  //   axios
  //     .get(
  //       `/api/favorites/check?userSeq=${user_seq}&hospitalSeq=${hospital_seq}`
  //     )
  //     .then((response) => {
  //       setIsFavorited(response.data.isFavorited);
  //     })
  //     .catch((error) => {
  //       console.error("찜 상태 확인 중 오류가 발생했습니다:", error);
  //     });
  // }, [user_seq, hospital_seq]);

  const handleFavoriteClick = () => {
    const action = isFavorited ? "delete" : "add";
    axiosApi
      .put(`/api/favorites/${action}`, {
        userSeq: userSeq,
        hospitalSeq: hospitalSeq,
      })
      .then(() => {
        setIsFavorited(!isFavorited);

      })
      .catch((error) => {
        console.error("찜 상태 변경 중 오류가 발생했습니다:", error);
      });
  };

  return (
    <IconButton onClick={handleFavoriteClick} color="primary">
      {isFavorited ? (
        <FavoriteIcon color="error" />
      ) : (
        <FavoriteBorderIcon color="error" />
      )}
    </IconButton>
  );
};

export default FavoriteButton;
