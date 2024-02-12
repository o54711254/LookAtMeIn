import React, { useState, useEffect } from "react";
import axios from "axios";
import Typography from "@mui/material/Typography";
import { useSelector } from "react-redux";
import axiosApi from "../../../api/axiosApi";

const WishList = () => {
  const [hospitalInfo, setHospitalInfo] = useState(null);
  const [category, setCategory] = useState(null);

  const user_seq = useSelector((state) => state.user.userSeq);

  useEffect(() => {
    axiosApi
      .get(`api/favorites/list/${user_seq}`)
      .then((response) => {
        console.log(response.data);
        if (response.data.message === "success") {
          setHospitalInfo(response.data.hospitalinfo);
          setCategory(response.data.category);
        } else {
          console.error(
            "데이터를 불러오는데 실패했습니다:",
            response.data.message
          );
        }
      })
      .catch((error) => {
        console.error("서버로부터 응답을 받는 중 오류가 발생했습니다:", error);
      });
  }, []);

  return (
    <div>
      {hospitalInfo && (
        <>
          <Typography variant="h6">{hospitalInfo.hospitalInfo_name}</Typography>
          <Typography>{hospitalInfo.hospitalInfo_introduce}</Typography>
          <Typography>{hospitalInfo.hospitalInfo_addresss}</Typography>
        </>
      )}
      {category && <Typography>{category.category_surgery}</Typography>}
    </div>
  );
};

export default WishList;
