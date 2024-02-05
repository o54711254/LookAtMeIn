import { Button } from "@mui/material";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axiosApi from "../../../api/axiosApi";

function MyInfo() {
  const user = useSelector((state) => state.user);
  const navigate = useNavigate();
  const [infoData, setInfoData] = useState({});
  useEffect(() => {
    axiosApi
      .get(`/api/mypage/${user.userSeq}`)
      .then((res) => {
        console.log(res.data);
        setInfoData(res.data);
      })
      .catch((error) => {
        console.log("데이터를 불러오는 중 에러 발생", error);
      });
  }, []);

  const onInfoUpdate = () => {
    navigate(`update`, { state: infoData });
  };
  return (
    <div>
      <div>이름 : {infoData.customerName}</div>
      <div>성별 : {infoData.customerGender}</div>
      <div>전화번호 : {infoData.customerPhoneNumber}</div>
      <div>이메일 : {infoData.customerEmail}</div>
      <Button onClick={onInfoUpdate}>정보 수정</Button>
    </div>
  );
}
export default MyInfo;
