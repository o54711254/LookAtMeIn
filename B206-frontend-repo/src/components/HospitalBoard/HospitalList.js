import React, { useState, useEffect } from "react";
import axios from "axios";
import Typography from "@mui/material/Typography";

const HospitalList = () => {
  const [hospitalInfo, setHospitalInfo] = useState(null);
  const [category, setCategory] = useState(null);

  useEffect(() => {
    axios
      .get(`/api/hospital/list`) // API 엔드포인트를 적절한 URL로 변경해주세요.
      .then((response) => {
        if (response.data.message === "success") {
          setHospitalInfo(response.data.responseObj.hospitalinfo);
          setCategory(response.data.responseObj.category);
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
      <h3>여기는 병원 리스트 페이지</h3>
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

export default HospitalList;
