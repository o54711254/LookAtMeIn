import React from "react";
import { Link } from "react-router-dom";
import { useState } from "react";
import * as yup from "yup";
import logo from "../../assets/lab_logo.png";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { Field, useFormik } from "formik";
import { toast, ToastContainer } from "react-toastify";
import axiosApi from "../../api/axiosApi.js";
import loginUser from "../../redux/user.js";
import { changeLoading, setToken } from "../../redux/auth";
import { BrowserRouter, Routes, Route } from "react-router-dom";

const validationSchema = yup.object({
  userId: yup
    .string()
    // .email("올바른 아이디를 입력해주세요.")
    .required("아이디 입력해주세요."),
  password: yup.string().required("패스워드를 입력해주세요."),
});

function LoginForm() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [membertype, setMembertype] = useState("");

  //멤버 타입(고객,병원 ,,,)이 라디오 타입으로 바꾸면 어떤 멤번지 바꿔주는 함수
  const handleChange = (e) => {
    const value = e.target.value;
    console.log(`선택한 값 : ${value}`);
    setMembertype(value);
  };

  const formik = useFormik({
    initialValues: {
      userId: "",
      password: "",
    },
    validationSchema: validationSchema,

    onSubmit: async (values) => {
      console.log(values);
      if (membertype === "customer") {
        //customer
        try {
          await axiosApi.post("/api/customer/login", values).then((res) => {
            //res는 서버에서 받은 응답 객체
<<<<<<< HEAD
            if (res.status === 201) {
=======
            if (res.status === 200) {
>>>>>>> dev-FE-오건영
              console.log("userData", res.data.username);
              window.alert("로그인 성공!");
              //로그인 성공
              dispatch(
                loginUser({
                  userSeq: res.data.responseObj.userSeq, // 사용자 일련번호
                  userId: res.data.responseObj.userId, // 사용자 아이디
                  userName: res.data.name, // 사용자 이름
                  userPw: res.data.responseObj.userPw, // 사용자 비밀번호
                  // role: res.data.responseObj.usertype, // 역할 업데이트
                })
              );

              //토큰 받아오기
              //서버에서 받은 토큰(authorization)을 사용하여 Redux 스토어에 토큰을 저장
              const accessToken = res.headers.get("authorization");
              dispatch(setToken({ accessToken: accessToken }));
              // toast.success(<h3>반갑습니다. 로그인이 완료되었습니다. </h3>, {
              //   // 토스트 메시지가 화면 상단 중앙에 나타나도록 하는 옵션
              //   position: toast.POSITION.TOP_CENTER,
              //   // 토스트 메시지가 자동으로 사라지는 시간을 밀리초 단위로 설정
              //   autoClose: 2000,
              // });
              window.alert("반갑습니다. 로그인이 완료되었습니다. ");
              // 로그인이 성공한 경우, 3초 후에 메인 홈으로 이동
              setTimeout(() => {
                navigate("/");
                dispatch(changeLoading());
              }, 3000);
            } else {
              // toast.error(<h3>아이디나 비밀번호를 다시 확인해 주세요.</h3>, {
              //   position: toast.POSITION.TOP_CENTER,
              //   autoClose: 2000,
              // });
              window.alert("아이디나 비밀번호를 다시 확인해 주세요.");
            }
          });
        } catch {}
      } else if (membertype === "hospital") {
        //hospital
        try {
          await axiosApi
            .post("/api/customer/login", values, {
              //values에는 이메일과 비밀번호가 담겨 있음
            })
            .then((res) => {
              //res는 서버에서 받은 응답 객체
              if (res.data.status === 200) {
                //로그인 성공
                console.log(res.data);
                console.log(res.data.responseObj);
                dispatch(
                  loginUser({
                    userSeq: res.data.responseObj.userSeq, // 사용자 일련번호
                    userId: res.data.responseObj.userId, // 사용자 아이디
                    userName: res.data.responseObj.userName, // 사용자 이름
                    userPw: res.data.responseObj.userPw, // 사용자 비밀번호
                    // role: res.data.responseObj.usertype, // 역할 업데이트
                  })
                );

                //토큰 받아오기
                //서버에서 받은 토큰(authorization)을 사용하여 Redux 스토어에 토큰을 저장
                const accessToken = res.headers.get("authorization");
                dispatch(setToken({ accessToken: accessToken }));
                // toast.success(<h3>반갑습니다. 로그인이 완료되었습니다. </h3>, {
                //   // 토스트 메시지가 화면 상단 중앙에 나타나도록 하는 옵션
                //   position: toast.POSITION.TOP_CENTER,
                //   // 토스트 메시지가 자동으로 사라지는 시간을 밀리초 단위로 설정
                //   autoClose: 2000,
                // });
                window.alert("반갑습니다. 로그인이 완료되었습니다. ");
                // 로그인이 성공한 경우, 3초 후에 특정 경로로 이동
                setTimeout(() => {
                  navigate("/hospital/mypage"); //병원 로그인 성공하면 병원 마이페이지로 이동//홈? 마페?
                  dispatch(changeLoading());
                }, 2000);
              } else {
                // toast.error(<h3>아이디나 비밀번호를 다시 확인해 주세요.</h3>, {
                //   position: toast.POSITION.TOP_CENTER,
                //   autoClose: 2000,
                // });
                window.alert("아이디나 비밀번호를 다시 확인해 주세요.");
              }
            });
        } catch {}
      } else if (membertype === "coordinator") {
        //coordinator
        try {
          await axiosApi
            .post("/api/customer/login", values, {
              //values에는 이메일과 비밀번호가 담겨 있음
              withCredentials: true, //CORS(Cross-Origin Resource Sharing) 정책을 따르는 웹 애플리케이션에서 발생하는 문제 중 하나를 해결하기 위한 옵션
            })
            .then((res) => {
              //res는 서버에서 받은 응답 객체
              if (res.data.status === 200) {
                //로그인 성공
                console.log(res.data);
                dispatch(
                  loginUser({
                    userSeq: res.data.responseObj.userSeq, // 사용자 일련번호
                    userId: res.data.responseObj.userId, // 사용자 아이디
                    userName: res.data.responseObj.userName, // 사용자 이름
                    userPw: res.data.responseObj.userPw, // 사용자 비밀번호
                    // role: res.data.responseObj.usertype, // 역할 업데이트
                  })
                );

                //토큰 받아오기
                //서버에서 받은 토큰(authorization)을 사용하여 Redux 스토어에 토큰을 저장
                const accessToken = res.headers.get("authorization");
                dispatch(setToken({ accessToken: accessToken }));
                // toast.success(<h3>반갑습니다. 로그인이 완료되었습니다. </h3>, {
                //   // 토스트 메시지가 화면 상단 중앙에 나타나도록 하는 옵션
                //   position: toast.POSITION.TOP_CENTER,
                //   // 토스트 메시지가 자동으로 사라지는 시간을 밀리초 단위로 설정
                //   autoClose: 2000,
                // });
                window.alert("반갑습니다. 로그인이 완료되었습니다. ");
                // 로그인이 성공한 경우, 3초 후에 메인 홈으로 이동
                setTimeout(() => {
                  navigate("/");
                  dispatch(changeLoading());
                }, 2000);
              } else {
                // toast.error(<h3>아이디나 비밀번호를 다시 확인해 주세요.</h3>, {
                //   position: toast.POSITION.TOP_CENTER,
                //   autoClose: 2000,
                // });
                window.alert("아이디나 비밀번호를 다시 확인해 주세요.");
              }
            });
        } catch {}
      } else if (membertype === "admin") {
        //admin
        try {
          await axiosApi
            .post("/api/customer/login", values, {
              //values에는 이메일과 비밀번호가 담겨 있음
              withCredentials: true, //CORS(Cross-Origin Resource Sharing) 정책을 따르는 웹 애플리케이션에서 발생하는 문제 중 하나를 해결하기 위한 옵션
            })
            .then((res) => {
              //res는 서버에서 받은 응답 객체
              if (res.data.status === 200) {
                //로그인 성공
                console.log(res.data);
                dispatch(
                  loginUser({
                    userSeq: res.data.responseObj.userSeq, // 사용자 일련번호
                    userId: res.data.responseObj.userId, // 사용자 아이디
                    userName: res.data.responseObj.userName, // 사용자 이름
                    userPw: res.data.responseObj.userPw, // 사용자 비밀번호
                    // role: res.data.responseObj.usertype, // 역할 업데이트
                  })
                );

                //토큰 받아오기
                //서버에서 받은 토큰(authorization)을 사용하여 Redux 스토어에 토큰을 저장
                const accessToken = res.headers.get("authorization");
                dispatch(setToken({ accessToken: accessToken }));
                // toast.success(<h3>반갑습니다. 로그인이 완료되었습니다. </h3>, {
                //   // 토스트 메시지가 화면 상단 중앙에 나타나도록 하는 옵션
                //   position: toast.POSITION.TOP_CENTER,
                //   // 토스트 메시지가 자동으로 사라지는 시간을 밀리초 단위로 설정
                //   autoClose: 2000,
                // });
                window.alert("반갑습니다. 로그인이 완료되었습니다. ");
                // 로그인이 성공한 경우, 3초 후에 메인 홈으로 이동
                setTimeout(() => {
                  navigate("/");
                  dispatch(changeLoading());
                }, 2000);
              } else {
                // toast.error(<h3>아이디나 비밀번호를 다시 확인해 주세요.</h3>, {
                //   position: toast.POSITION.TOP_CENTER,
                //   autoClose: 2000,
                // });
                window.alert("아이디나 비밀번호를 다시 확인해 주세요.");
              }
            });
        } catch {}
      } else {
        // toast.error(<h3>회원유형을 선택해주세요 !</h3>, {
        //   position: toast.POSITION.TOP_CENTER,
        //   autoClose: 1000,
        //   hideProgressBar: true,
        // });
        window.alert("회원유형을 선택해주세요 !");
      }
    },
  });

  return (
    // <div className="loginForm">
    //   <div>
    //     <img src={logo} alt="로고사진" />
    //     <h1>룩앳미인</h1>
    //   </div>

    //   <h3>아이디</h3>
    //   <input type="text" placeholder="abcdef@ssafy.com" />
    //   <h3>비밀번호</h3>
    //   <input
    //     type="password"
    //     placeholder="영문, 숫자, 특수문자 조합 8~16자를 입력하세요"
    //   />
    //   <div className="loginButton">
    //     <button type="submit">로그인</button>
    //   </div>
    //   <div>
    //     <Link to="/Regist">회원가입 </Link> |
    //     <Link to="/FindPassword"> 비밀번호 찾기</Link>
    //   </div>
    // </div>

    <div>
      <div className="로그인 창 시작">
        <img src={logo} alt="로고사진" />
        <h1>룩앳미인</h1>
      </div>

      <form onSubmit={formik.handleSubmit}>
        <div className="radioButtons">
          <div className="radioButton">
            <input
              id="admin"
              value="admin"
              name="membertype"
              type="radio"
              onChange={handleChange}
            ></input>
            <label htmlFor="admin">관리자</label>
          </div>
          <div className="radioButton">
            <input
              id="customer"
              value="customer"
              type="radio"
              name="membertype"
              onChange={handleChange}
            ></input>
            <label htmlFor="customer">고객</label>
          </div>
          <div className="radioButton">
            <input
              id="hospital"
              value="hospital"
              name="membertype"
              type="radio"
              onChange={handleChange}
            ></input>
            <label htmlFor="hospital">병원</label>
          </div>
          <div className="radioButton">
            <input
              id="coordinator"
              value="coordinator"
              name="membertype"
              type="radio"
              onChange={handleChange}
            ></input>
            <label htmlFor="coordinator">코디네이터</label>
          </div>
        </div>

        <div className="idInput">
          <h3>아이디</h3>
          <input
            name="userId"
            placeholder="아이디를 입력해주세요"
            value={formik.values.userId}
            onChange={formik.handleChange}
            type="text"
            className={formik.touched.id && formik.errors.id ? "error" : ""}
          />
          {formik.touched.id && formik.errors.id && (
            <div className="helperText">{formik.errors.id}</div>
          )}
        </div>
        <div className="passwordInput">
          <h3>비밀번호</h3>
          <input
            name="password"
            placeholder="비밀번호를 입력해주세요"
            value={formik.values.password}
            onChange={formik.handleChange}
            type="password"
            className={
              formik.touched.password && formik.errors.password ? "error" : ""
            }
          />
          {formik.touched.password && formik.errors.password && (
            <div className="helperText">{formik.errors.password}</div>
          )}
        </div>
        <button type="submit">로그인</button>
        <div>
          <Link to="/regist">회원가입</Link>
          <Link to="/findPassword"> 비밀번호 찾기</Link>
        </div>
      </form>
    </div>
  );
}
export default LoginForm;
