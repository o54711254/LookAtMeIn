import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import axiosApi from "axios";
import logo from "../../assets/lab_logo.png";

function FindPassword() {
  const handleSubmit = async (e) => {
    e.preventDefault();
    const email = e.target.emil.value; //input에서 입력받은 이메일 값

    try {
      //이메일 존재 여부 확인
      const response = await axiosApi.post("/customer/resetpw", { email });

      if (response.status === 200) {
        //꼭 status 아니어도 ㄱㅊ 백이랑 담에 얘기해봐서 해당 emil유저 있으면 뭐라고 답할지 얘기해봐야징
        window.alert("임시 비밀번호가 입력하신 이메일로 발송되었습니다.");
      } else {
        window.alert("임시 비밀번호가 입력하신 이메일로 발송되었습니다.");
      }
    } catch (error) {
      console.error("에러남!! : ", error);
    }
  };

  return (
    <>
      <div className="FindPasswordForm">
        <div>
          <img src={logo} alt="로고사진" />
          <h1>룩앳미인</h1>
        </div>

        <form onSubmit={handleSubmit}>
          <h3>이메일</h3>
          <input
            name="email"
            type="text"
            placeholder="임시 비밀번호를 발급받을 이메일을 입력해주세요."
          />
          <button type="submit">비밀번호 찾기</button>
        </form>
      </div>
    </>
  );
}
export default FindPassword;
