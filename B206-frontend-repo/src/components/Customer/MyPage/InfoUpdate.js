import { useSelector } from "react-redux";
import { useLocation } from "react-router-dom";
import { useState } from "react";
import axiosApi from "../../../api/axiosApi";

function InfoUpdate() {
  const location = useLocation();
  const user = useSelector((state) => state.user);
  const initialData = location.state || user;

  const [updateData, setUpdateData] = useState({
    userId: initialData.userId,
    userName: initialData.userName,
    password: "", // 보안을 위해 비밀번호는 초기화
  });

  const handleInputChange = (e) => {
    setUpdateData({
      ...updateData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(user.userSeq);
    axiosApi
      .put(`/api/user/update/${user.userSeq}`, updateData)
      .then((res) => {
        console.log(res.data);
      })
      .catch((error) => {
        console.error("유저 정보 수정 중 에러 발생", error);
      });
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="userId">아이디:</label>
          <input
            id="userId"
            name="userId"
            type="text"
            value={updateData.userId}
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label htmlFor="userName">이름:</label>
          <input
            id="userName"
            name="userName"
            type="text"
            value={updateData.userName}
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label htmlFor="password">비밀번호:</label>
          <input
            id="password"
            name="password"
            type="password"
            value={updateData.password}
            onChange={handleInputChange}
          />
        </div>
        <button type="submit">정보 수정</button>
      </form>
    </div>
  );
}
export default InfoUpdate;
