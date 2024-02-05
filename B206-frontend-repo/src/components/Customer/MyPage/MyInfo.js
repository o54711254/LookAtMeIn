import { Button } from "@mui/material";
import { useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

function MyInfo() {
  const user = useSelector((state) => state.user);
  const navigate = useNavigate();
  console.log(user.data);

  const onInfoUpdate = () => {
    navigate(`update`, { state: user });
  };
  return (
    <div>
      <div>아이디 : {user.userId}</div>
      <div>이름 : {user.userName}</div>
      <div>시발 수정 또 뭐해야돼</div>
      <Button onClick={onInfoUpdate}>정보 수정</Button>
    </div>
  );
}
export default MyInfo;
