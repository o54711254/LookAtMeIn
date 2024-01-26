import React from "react";
import { Link } from "react-router-dom";
import { useState } from "react";
import { useFormik } from "formik";
import * as yup from "yup";
import axiosAPi from "../../api/axiosApi";
import logo from "../../assets/lab_logo.png";
import { useNavigate } from "react-router-dom";
// import { BrowserRouter, Routes, Route } from "react-router-dom";

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
  phoneNumber: yup
    .string()
    .matches(/^[0-9]+$/, "숫자만 입력하세요.")
    .required("전화번호를 입력하세요."),
  birthdate: yup
    .string()
    .matches(
      /^(19|20)\d\d\.(0[1-9]|1[0-2])\.(0[1-9]|[12][0-9]|3[01])$/,
      "생년월일을 올바른 형식으로 입력하세요 (yyyy.mm.dd)."
    )
    .required("생년월일을 입력하세요."),
});

function UserRegistForm() {
  // 페이지 간 이동을 담당하는 함수. (해당 경로로 페이지가 이동함)
  const navigate = useNavigate();
  //사용 가능한 정보인지 검사
  const [useable, setUseable] = useState(null);
  //아이디 중복체크
  const checkDuplicateId = (e) => {
    e.preventDefault();
    axiosAPi.get(`/user/${formik.values.id}`).then((response) => {
      console.log(response.data);
      if (response.status === 200) {
        //alert("사용 불가한 아이디입니다."); //이미 사용중인 아이디
        setUseable(false);
      } else if (response.status === 204) {
        //alert("사용 가능한 아이디입니다.");
        setUseable(true);
      } else {
        alert("사용 불가한 아이디입니다.");
      }
    });
  };

  const formik = useFormik({
    initialValues: {
      id: "",
      password: "",
      confirmPassword: "",
      name: "",
      gender: "",
      birth: "",
      phoneNumber: "",
      email: "",
      address: "",
    },
    validationSchema,
    onSubmit: async (values) => {
      const {
        id,
        password,
        confirmPassword,
        name,
        gender,
        birth,
        phoneNumber,
        email,
        address,
      } = values;
      if (!useable) {
        window.alert("아이디가 중복됩니다. 다시 시도해주세요.");
      } else {
        try {
          await axiosAPi.post("/customer/regist", {
            id,
            password,
            confirmPassword,
            name,
            gender,
            birth,
            phoneNumber,
            email,
            address,
          });
          window.alert("회원가입이 완료되었습니다.");
          setTimeout(() => {
            navigate("/login"); //로그인창으로 바로 이동
          }, 3000);
        } catch {}
      }
    },
  });

  return (
    <>
      <div className="UserRegistForm">
        <div>
          <img src={logo} alt="로고사진" />
          <h1>룩앳미인</h1>
        </div>
        <form onSubmit={formik.handleSubmit}>
          <h3>아이디</h3>
          <input
            type="text"
            placeholder="abcd1234"
            name="id"
            value={formik.values.id}
          />
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
        </form>
      </div>
    </>
  );
}
export default UserRegistForm;
