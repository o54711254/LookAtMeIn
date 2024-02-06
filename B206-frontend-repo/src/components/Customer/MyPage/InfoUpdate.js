import { useSelector } from "react-redux";
import { useLocation } from "react-router-dom";
import { useState } from "react";
import axiosApi from "../../../api/axiosApi";

function InfoUpdate() {
  const location = useLocation();
  const user = useSelector((state) => state.user);
  const userData = location.state || user;
  const [updateData, setUpdateData] = useState(userData);

  const handleSubmit = (event) => {
    event.preventDefault();
    // 폼 제출 로직 처리
    console.log("업데이트된 유저 ID:", updateData.userId);
    console.log("업데이트된 유저 이름:", updateData.userName);
    // 비밀번호는 콘솔에 출력하지 않음
    axiosApi
      .put(`/api/user/update/${user.userSeq}`, updateData)
      .then((res) => {
        console.log(res.data);
      })
      .catch((error) => {
        console.log("유저정보 수정 중 에러 발생", error);
      });
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="userId">아이디:</label>
          <input
            id="userId"
            type="text"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="userName">이름:</label>
          <input
            id="userName"
            type="text"
            value={userName}
            onChange={(e) => setUserName(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="password">비밀번호:</label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button type="submit">정보 수정</button>
      </form>
    </div>
  );
}
export default InfoUpdate;
