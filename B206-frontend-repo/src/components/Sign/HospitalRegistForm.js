import React from "react";
import { Link } from "react-router-dom";
import { useState } from "react";
import axiosApi from "axios";
import * as yup from "yup";
import { useNavigate } from "react-router-dom";
import { useFormik } from "formik";

import logo from "../../assets/lab_logo.png";
import axiosAPi from "../../api/axiosApi";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

//회원가입 시 입력하는 정보 유효성 검사
const validationSchema = yup.object({
  id: yup.string().required("아이디를 입력하세요."),
  password: yup
    .string()
    .matches(
      /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*+=-]).{8,16}$/,
      "영문+숫자+특수문자 조합으로 8~16글자를 입력하세요."
    )
    .required("비밀번호를 입력하세요."),
  confirmPassword: yup
    .string()
    .oneOf([yup.ref("password"), null], "비밀번호가 일치하지 않습니다.")
    .required("비밀번호를 다시 입력하세요."),
  email: yup
    .string()
    .email("올바른 이메일 형식을 입력하세요.")
    .required("이메일을 입력하세요."),
  homepage: yup
    .string()
    .url("올바른 홈페이지 주소를 입력하세요.")
    .required("홈페이지 주소를 입력하세요."),
  phoneNumber: yup
    .string()
    .matches(/^[0-9]+$/, "숫자만 입력하세요.")
    .required("전화번호를 입력하세요."),
});

function HospitalRegistForm() {
  const navigate = useNavigate();

  // id: "",
  //     password: "",
  //     confirmPassword: "",
  //     hospitalName: "",
  //     address: "",
  //     homepage: "",
  //     businessRegistrationNumber: "",
  //     businessRegistrationCertificate: "",
  //     hospitalOwnerName: "",
  //     email: "",
  //     phoneNumber: "",

  //사업자등록증
  const [businessRegistrationCertificate, setBusinessRegistrationCertificate] =
    useState([]);

  //사업자등록증 업로드될때 작동함
  const onChange = (e) => {
    const uploadimg = e.target.files[0];
    setBusinessRegistrationCertificate(uploadimg);
  };

  return (
    <>
      <div className="hospitalRegistForm">
        <img src={logo} alt="로고사진" />
        <div className="LABRegist">룩앳미인</div>
        <form>
          <h3>아이디</h3>
          <input
            type="text"
            id="id"
            placeholder="abcdef"
            // value={formik.values.id}
            // onChange={formik.handleChange}
            // error={formik.touched.id && Boolean(formik.errors.id)}
            // helperText={formik.touched.id ? formik.errors.id : ""}
          />

          {/* onClick={checkDuplicateId} */}
          <button>중복확인</button>
          {/* {useable === null ? (
            ""
          ) : useable ? (
            <span>사용가능한 아이디입니다.</span>
          ) : (
            <span>이미 사용중인 아이디 입니다.</span>
          )} */}
          <h3>비밀번호</h3>
          <input
            type="password"
            placeholder="영문, 숫자, 특수문자 조합 8~16자를 입력하세요"
            // value={formik.values.password}
            // onChange={formik.handleChange}
            // error={formik.touched.password && Boolean(formik.errors.password)}
            // helperText={formik.touched.password ? formik.errors.password : ""}
            autoComplete="off" //자동 완성을 비활성화
          />
          <h3>비밀번호 확인</h3>
          <input
            type="password"
            placeholder="비밀번호 확인"
            autoComplete="off"
            // value={formik.values.confirmPassword}
            // onChange={formik.handleChange}
            // error={
            //   formik.touched.confirmPassword &&
            //   Boolean(formik.errors.confirmPassword)
            // }
            // helperText={
            //   formik.touched.confirmPassword
            //     ? formik.errors.confirmPassword
            //     : ""
            // }
          />
          <h3>병원명</h3>
          <input
            type="text"
            placeholder="한글 병원명"
            // value={formik.values.hospitalName}
            // onChange={formik.handleChange}
          />
          <h3>주소</h3>
          <input
            type="text"
            placeholder="주소 입력"
            // value={formik.values.address}
            // onChange={formik.handleChange}
          />

          {/* onClick={handleAddressInput} */}
          <button>주소 입력</button>
          <h3>홈페이지</h3>
          <input
            type="text"
            placeholder="URL"
            // value={formik.values.homepage}
            // onChange={formik.handleChange}
          />
          <h3>사업자 등록번호</h3>
          <input
            type="text"
            placeholder="T사업자 등록번호"
            // value={formik.values.businessRegistrationNumber}
            // onChange={formik.handleChange}
          />
          <h3>사업자 등록증</h3>
          <input
            type="text"
            placeholder="주소 사업자 등록증"
            // value={formik.values.businessRegistrationCertificate}
            // onChange={formik.handleChange}
          />
          <button>파일 선택</button>
          <h3>병원장 이름</h3>
          <input
            type="text"
            placeholder="병원장 이름"
            // value={formik.values.hospitalOwnerName}
            // onChange={formik.handleChange}
          />
          <h3>이메일</h3>
          <input
            type="text"
            placeholder="abcdef@ssafy.com"
            // value={formik.values.email}
            // onChange={formik.handleChange}
          />
          <h3>전화번호</h3>
          <input
            type="text"
            placeholder="042-000-1234"
            // value={formik.values.phoneNumber}
            // onChange={formik.handleChange}
          />
          <div className="RegistButton">
            <button type="submit">회원가입</button>
          </div>
        </form>
      </div>
    </>
  );
}
export default HospitalRegistForm;
