import React from "react";
import { Link } from "react-router-dom";
import { useState } from "react";

import logo from "../../assets/lab_logo.png";

// import { BrowserRouter, Routes, Route } from "react-router-dom";

function HospitalRegistForm() {
  return (
    <div className="HospitalRegistForm">
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
      <h3>병원명</h3>
      <input type="text" placeholder="한글 병원명" />
      <h3>주소</h3>
      <input type="text" placeholder="주소 입력" />
      <button>주소 입력</button>
      <h3>홈페이지</h3>
      <input type="text" placeholder="URL" />
      <h3>사업자 등록번호</h3>
      <input type="text" placeholder="T사업자 등록번호" />
      <h3>사업자 등록증</h3>
      <input type="text" placeholder="주소 사업자 등록증" />
      <button>파일 선택</button>
      <h3>병원장 이름</h3>
      <input type="text" placeholder="병원장 이름" />
      <h3>이메일</h3>
      <input type="text" placeholder="abcdef@ssafy.com" />
      <h3>전화번호</h3>
      <input type="text" placeholder="042-000-0000" />

      <div className="RegistButton">
        <button type="submit">회원가입</button>
      </div>
    </div>
  );
}
export default HospitalRegistForm;
