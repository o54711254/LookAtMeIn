import React from "react";
import { Link } from "react-router-dom";
import { useState } from "react";

import logo from "../../assets/lab_logo.png";

// import { BrowserRouter, Routes, Route } from "react-router-dom";

function UserRegistForm() {
  return (
    <div className="UserRegistForm">
      <div>
        <img src={logo} alt="로고사진" />
        <h1>룩앳미인</h1>
      </div>

      <h3>아이디</h3>
      <input type="text" placeholder="abcdef" />
      <button>중복확인</button>
      <h3>비밀번호</h3>
      <input
        type="password"
        placeholder="영문, 숫자, 특수문자 조합 8~16자를 입력하세요"
      />
      <h3>비밀번호 확인</h3>
      <input type="password" placeholder="비밀번호 확인" />
      <h3>이름</h3>
      <input type="text" placeholder="한글 이름" />
      <h3>닉네임</h3>
      <input type="text" placeholder="닉네임" />
      <h3>생년월일</h3>
      <input type="text" placeholder="1999.01.01" />
      <h3>전화번호</h3>
      <input type="text" placeholder="010-0000-0000" />
      <h3>주소</h3>
      <input type="text" placeholder="주소 입력" />
      <button>주소 입력</button>
      <h3>이메일</h3>
      <input type="text" placeholder="abcdef@ssafy.com" />

      <div className="RegistButton">
        <button type="submit">회원가입</button>
      </div>
    </div>
  );
}
export default UserRegistForm;
