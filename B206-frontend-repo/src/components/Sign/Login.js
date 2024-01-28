import React from "react";
// import { Link } from "react-router-dom";
import { useState } from "react";
import LoginForm from "./LoginForm";
import * as yup from "yup";
import { Navigate, useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { Field, useFormik } from "formik";
import { toast, ToastContainer } from "react-toastify";
import axiosApi from "axios";
import loginUser from "../../redux/user.js";
import { changeLoading, setToken } from "../../redux/auth";
import logo from "../../assets/lab_logo.png";
import { BrowserRouter, Routes, Route } from "react-router-dom";

// const validationSchema = yup.object({
//   id: yup
//     .string()
//     // .email("올바른 아이디를 입력해주세요.")
//     .required("아이디 입력해주세요."),
//   password: yup.string().required("패스워드를 입력해주세요."),
// });

// //토큰받아서 로그인 관리하는 페이지
function Login() {
  //   const navigate = useNavigate();
  //   const dispatch = useDispatch();

  //   const [usertype, setUsertype] = useState("");

  //   const handleChange = (e) => {
  //     console.log(`선택한 값 : ${e.target.value}`);
  //     setUsertype(e.target.value);
  //   };

  //   const formik = useFormik({
  //     initialValues: {
  //       id: "",
  //       password: "",
  //     },
  //     validationSchema: validationSchema,

  //     onSubmit: async (values) => {
  //       if (usertype === "customer") {
  //         try {
  //           await axiosApi
  //             .post("/customer/login", values, {
  //               //values에는 이메일과 비밀번호가 담겨 있음
  //               withCredentials: true, //CORS(Cross-Origin Resource Sharing) 정책을 따르는 웹 애플리케이션에서 발생하는 문제 중 하나를 해결하기 위한 옵션
  //             })
  //             .then((res) => {
  //               //res는 서버에서 받은 응답 객체
  //               if (res.data.status === 200) {
  //                 //로그인 성공
  //                 console.log(res.data);
  //                 dispatch(
  //                   loginUser({
  //                     userSeq: res.data.responseObj.userSeq, // 사용자 일련번호
  //                     userId: res.data.responseObj.userId, // 사용자 아이디
  //                     userName: res.data.responseObj.userName, // 사용자 이름
  //                     userPw: res.data.responseObj.userPw, // 사용자 비밀번호
  //                     role: res.data.responseObj.usertype, // 역할 업데이트
  //                   })
  //                 );

  //                 //토큰 받아오기
  //                 //서버에서 받은 토큰(authorization)을 사용하여 Redux 스토어에 토큰을 저장
  //                 const accessToken = res.headers.get("authorization");
  //                 dispatch(setToken({ accessToken: accessToken }));
  //                 toast.success(<h3>반갑습니다. 로그인이 완료되었습니다. </h3>, {
  //                   // 토스트 메시지가 화면 상단 중앙에 나타나도록 하는 옵션
  //                   position: toast.POSITION.TOP_CENTER,
  //                   // 토스트 메시지가 자동으로 사라지는 시간을 밀리초 단위로 설정
  //                   autoClose: 2000,
  //                 });
  //                 // 로그인이 성공한 경우, 3초 후에 메인 홈으로 이동
  //                 setTimeout(() => {
  //                   navigate("/");
  //                   dispatch(changeLoading());
  //                 }, 2000);
  //               } else {
  //                 toast.error(<h3>아이디나 비밀번호를 다시 확인해 주세요.</h3>, {
  //                   position: toast.POSITION.TOP_CENTER,
  //                   autoClose: 2000,
  //                 });
  //               }
  //             });
  //         } catch {}
  //       } else if (usertype === "hospital") {
  //         try {
  //           await axiosApi
  //             .post("/hospital/login", values, {
  //               //values에는 이메일과 비밀번호가 담겨 있음
  //               withCredentials: true, //CORS(Cross-Origin Resource Sharing) 정책을 따르는 웹 애플리케이션에서 발생하는 문제 중 하나를 해결하기 위한 옵션
  //             })
  //             .then((res) => {
  //               //res는 서버에서 받은 응답 객체
  //               if (res.data.status === 200) {
  //                 console.log(res.data);
  //                 //로그인 성공
  //                 console.log(res.data);
  //                 dispatch(
  //                   loginUser({
  //                     userSeq: res.data.responseObj.userSeq, // 사용자 일련번호
  //                     userId: res.data.responseObj.userId, // 사용자 아이디
  //                     userName: res.data.responseObj.userName, // 사용자 이름
  //                     userPw: res.data.responseObj.userPw, // 사용자 비밀번호
  //                     role: res.data.responseObj.usertype, // 역할 업데이트
  //                   })
  //                 );

  //                 //토큰 받아오기
  //                 //서버에서 받은 토큰(authorization)을 사용하여 Redux 스토어에 토큰을 저장
  //                 const accessToken = res.headers.get("authorization");
  //                 dispatch(setToken({ accessToken: accessToken }));
  //                 toast.success(<h3>반갑습니다. 로그인이 완료되었습니다. </h3>, {
  //                   // 토스트 메시지가 화면 상단 중앙에 나타나도록 하는 옵션
  //                   position: toast.POSITION.TOP_CENTER,
  //                   // 토스트 메시지가 자동으로 사라지는 시간을 밀리초 단위로 설정
  //                   autoClose: 2000,
  //                 });
  //                 // 로그인이 성공한 경우, 3초 후에 특정 경로로 이동
  //                 setTimeout(() => {
  //                   navigate("/hospital/mypage"); //병원 로그인 성공하면 병원 마이페이지로 이동
  //                   dispatch(changeLoading());
  //                 }, 2000);
  //               } else {
  //                 toast.error(<h3>아이디나 비밀번호를 다시 확인해 주세요.</h3>, {
  //                   position: toast.POSITION.TOP_CENTER,
  //                   autoClose: 2000,
  //                 });
  //               }
  //             });
  //         } catch {}
  //       } else if (usertype === "coodinator") {
  //         try {
  //           await axiosApi
  //             .post("/codinator/login", values, {
  //               //values에는 이메일과 비밀번호가 담겨 있음
  //               withCredentials: true, //CORS(Cross-Origin Resource Sharing) 정책을 따르는 웹 애플리케이션에서 발생하는 문제 중 하나를 해결하기 위한 옵션
  //             })
  //             .then((res) => {
  //               //res는 서버에서 받은 응답 객체
  //               if (res.data.status === 200) {
  //                 //로그인 성공
  //                 console.log(res.data);
  //                 dispatch(
  //                   loginUser({
  //                     userSeq: res.data.responseObj.userSeq, // 사용자 일련번호
  //                     userId: res.data.responseObj.userId, // 사용자 아이디
  //                     userName: res.data.responseObj.userName, // 사용자 이름
  //                     userPw: res.data.responseObj.userPw, // 사용자 비밀번호
  //                     role: res.data.responseObj.usertype, // 역할 업데이트
  //                   })
  //                 );

  //                 //토큰 받아오기
  //                 //서버에서 받은 토큰(authorization)을 사용하여 Redux 스토어에 토큰을 저장
  //                 const accessToken = res.headers.get("authorization");
  //                 dispatch(setToken({ accessToken: accessToken }));
  //                 toast.success(<h3>반갑습니다. 로그인이 완료되었습니다. </h3>, {
  //                   // 토스트 메시지가 화면 상단 중앙에 나타나도록 하는 옵션
  //                   position: toast.POSITION.TOP_CENTER,
  //                   // 토스트 메시지가 자동으로 사라지는 시간을 밀리초 단위로 설정
  //                   autoClose: 2000,
  //                 });
  //                 // 로그인이 성공한 경우, 3초 후에 메인 홈으로 이동
  //                 setTimeout(() => {
  //                   navigate("/");
  //                   dispatch(changeLoading());
  //                 }, 2000);
  //               } else {
  //                 toast.error(<h3>아이디나 비밀번호를 다시 확인해 주세요.</h3>, {
  //                   position: toast.POSITION.TOP_CENTER,
  //                   autoClose: 2000,
  //                 });
  //               }
  //             });
  //         } catch {}
  //       } else if (usertype === "admin") {
  //         try {
  //           await axiosApi
  //             .post("/admin", values, {
  //               //values에는 이메일과 비밀번호가 담겨 있음
  //               withCredentials: true, //CORS(Cross-Origin Resource Sharing) 정책을 따르는 웹 애플리케이션에서 발생하는 문제 중 하나를 해결하기 위한 옵션
  //             })
  //             .then((res) => {
  //               //res는 서버에서 받은 응답 객체
  //               if (res.data.status === 200) {
  //                 //로그인 성공
  //                 console.log(res.data);
  //                 dispatch(
  //                   loginUser({
  //                     userSeq: res.data.responseObj.userSeq, // 사용자 일련번호
  //                     userId: res.data.responseObj.userId, // 사용자 아이디
  //                     // userName: res.data.responseObj.userName, // 사용자 이름
  //                     userPw: res.data.responseObj.userPw, // 사용자 비밀번호
  //                     role: res.data.responseObj.usertype, // 역할 업데이트
  //                   })
  //                 );

  //                 //토큰 받아오기
  //                 //서버에서 받은 토큰(authorization)을 사용하여 Redux 스토어에 토큰을 저장
  //                 const accessToken = res.headers.get("authorization");
  //                 dispatch(setToken({ accessToken: accessToken }));
  //                 toast.success(<h3>반갑습니다. 로그인이 완료되었습니다. </h3>, {
  //                   // 토스트 메시지가 화면 상단 중앙에 나타나도록 하는 옵션
  //                   position: toast.POSITION.TOP_CENTER,
  //                   // 토스트 메시지가 자동으로 사라지는 시간을 밀리초 단위로 설정
  //                   autoClose: 2000,
  //                 });
  //                 // 로그인이 성공한 경우, 3초 후에 메인 홈으로 이동
  //                 setTimeout(() => {
  //                   navigate("/");
  //                   dispatch(changeLoading());
  //                 }, 2000);
  //               } else {
  //                 toast.error(<h3>아이디나 비밀번호를 다시 확인해 주세요.</h3>, {
  //                   position: toast.POSITION.TOP_CENTER,
  //                   autoClose: 2000,
  //                 });
  //               }
  //             });
  //         } catch {}
  //       } else {
  //         toast.error(<h3>회원유형을 선택해주세요 !</h3>, {
  //           position: toast.POSITION.TOP_CENTER,
  //           autoClose: 1000,
  //           hideProgressBar: true,
  //         });
  //       }
  //     },
  //   });
  const [token, setToken] = useState(null);
  return (
    <div className="Login">
      <img src={logo} alt="로고사진" />
      <div className="LABRegist">룩앳미인</div>

      <LoginForm />
    </div>
  );
}
export default Login;
