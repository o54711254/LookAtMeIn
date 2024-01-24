import React from "react";
import { Link } from "react-router-dom";
import { useState } from "react";

import logo from "../../assets/lab_logo.png";

// import { BrowserRouter, Routes, Route } from "react-router-dom";

function loginForm() {
  return (
    <div className="loginForm">
      <div>
        <img src={logo} alt="로고사진" />
        <h1>룩앳미인</h1>
      </div>

      <h3>아이디</h3>
      <input type="text" placeholder="abcdef@ssafy.com" />
      <h3>비밀번호</h3>
      <input
        type="password"
        placeholder="영문, 숫자, 특수문자 조합 8~16자를 입력하세요"
      />
      <div className="loginButton">
        <button type="submit">로그인</button>
      </div>
      <div>
        <Link to="/Regist">회원가입 </Link> |
        <Link to="/FindPassword"> 비밀번호 찾기</Link>
      </div>
    </div>
  );
}
export default loginForm;
