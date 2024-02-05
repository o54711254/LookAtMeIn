import { useSelector } from "react-redux";
import { useLocation } from "react-router-dom";
import { useState } from "react";
import axiosApi from "../../../api/axiosApi";

function InfoUpdate() {
  const location = useLocation();
  const user = useSelector((state) => state.user);
  const initialData = location.state || user;

  const [updateData, setUpdateData] = useState({
    customerName: initialData.customerName,
    password: "", // 보안을 위해 비밀번호는 초기화
    customerEmail: initialData.customerEmail,
    customerPhoneNumber: initialData.customerPhoneNumber,
  });

  const handleInputChange = (e) => {
    setUpdateData({
      ...updateData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
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
          <label htmlFor="customerEmail">이름:</label>
          <input
            id="customerName"
            name="customerName"
            type="name"
            value={updateData.customerName}
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label htmlFor="customerEmail">이메일:</label>
          <input
            id="customerEmail"
            name="customerEmail"
            type="email"
            value={updateData.customerEmail}
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label htmlFor="customerPhoneNumber">전화번호:</label>
          <input
            id="customerPhoneNumber"
            name="customerPhoneNumber"
            type="text"
            value={updateData.customerPhoneNumber}
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
