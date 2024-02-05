import { useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";
import axiosApi from "../../../api/axiosApi";

function InfoUpdate() {
  const location = useLocation();
  const navigate = useNavigate();
  const user = useSelector((state) => state.user);
  const initialData = location.state || user;

  const [updateData, setUpdateData] = useState({
    userId: initialData.userId,
    userPassword: "", // 보안을 위해 비밀번호는 초기화
    customerName: initialData.customerName,
    customerGender: initialData.customerGender,
    customerPhoneNumber: initialData.customerPhoneNumber,
    customerEmail: initialData.customerEmail,
    customerAddress: initialData.customerAddress,
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
      .put(`/api/mypage/user/${user.userSeq}`, updateData)
      .then((res) => {
        console.log(res.data);
        navigate(`/mypage/info`);
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
          <label htmlFor="userPassword">비밀번호:</label>
          <input
            id="userPassword"
            name="userPassword"
            type="password"
            value={updateData.userPassword}
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label htmlFor="customerName">이름:</label>
          <input
            id="customerName"
            name="customerName"
            type="text"
            value={updateData.customerName}
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label htmlFor="customerGender">성별:</label>
          <input
            id="customerGender"
            name="customerGender"
            type="text"
            value={updateData.customerGender}
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
          <label htmlFor="customerAddress">주소:</label>
          <input
            id="customerAddress"
            name="customerAddress"
            type="text"
            value={updateData.customerAddress}
            onChange={handleInputChange}
          />
        </div>
        <button type="submit">정보 수정</button>
      </form>
    </div>
  );
}
export default InfoUpdate;
