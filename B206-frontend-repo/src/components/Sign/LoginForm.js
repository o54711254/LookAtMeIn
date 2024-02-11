import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { useFormik } from "formik";
import * as yup from "yup";
import axiosApi from "../../api/axiosApi";
import { loginUser } from "../../redux/user";
import { setToken } from "../../redux/auth";
import styles from "./LoginForm.module.css";
import logo from "../../assets/logo.png";
import profile from "../../assets/profile2.png"; // 기본 프로필 이미지

const validationSchema = yup.object({
  userId: yup.string().required("아이디 입력해주세요."),
  userPassword: yup.string().required("패스워드를 입력해주세요."),
});

function LoginForm() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [profileImg, setProfileImg] = useState(profile); // 기본값으로 profile 설정

  const formik = useFormik({
    initialValues: {
      userId: "",
      userPassword: "",
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      try {
        const loginResponse = await axiosApi.post("/api/user/login", values);
        if (loginResponse.status === 200) {
          const profileResponse = await axiosApi.get(
            `/api/mypage/${loginResponse.data.userSeq}`
          );
          let imageData = profile; // 기본 프로필 이미지
          if (profileResponse.data.base64 && profileResponse.data.type) {
            const { base64, type } = profileResponse.data;
            imageData = `data:${type};base64,${base64}`;
          }
          setProfileImg(imageData); // 상태 업데이트

          dispatch(
            loginUser({
              userSeq: loginResponse.data.userSeq,
              profileImg: imageData, // 업데이트된 이미지 데이터
              userId: profileResponse.data.userId,
              userPassword: profileResponse.data.userPassword,
              userName: profileResponse.data.customerName,
              userEmail: profileResponse.data.customerEmail,
              role: loginResponse.data.userType,
            })
          );

          const { accessToken } = loginResponse.data.tokenInfo;
          dispatch(setToken({ accessToken }));
          alert("반갑습니다. 로그인이 완료되었습니다.");
          navigate("/");
        } else {
          alert("아이디나 비밀번호를 다시 확인해 주세요.");
        }
      } catch (error) {
        alert("아이디나 비밀번호를 다시 확인해 주세요.");
      }
    },
  });

  return (
    <div className={styles.loginFormContainer}>
      <div className={styles.logo}>
        <img src={logo} alt="로고" className={styles.logoIcon} />
      </div>
      <form onSubmit={formik.handleSubmit} className={styles.submitBox}>
        {/* 아이디 입력 */}
        <div className={styles.inputBox}>
          <input
            name="userId"
            placeholder="아이디를 입력해주세요"
            value={formik.values.userId}
            onChange={formik.handleChange}
            type="text"
            className={
              formik.touched.userId && formik.errors.userId ? "error" : ""
            }
            id={styles.input}
          />
          {formik.touched.userId && formik.errors.userId && (
            <div>{formik.errors.userId}</div>
          )}
        </div>
        {/* 비밀번호 입력 */}
        <div className={styles.inputBox}>
          <input
            name="userPassword"
            placeholder="비밀번호를 입력해주세요"
            value={formik.values.userPassword}
            onChange={formik.handleChange}
            type="password"
            className={
              formik.touched.userPassword && formik.errors.userPassword
                ? "error"
                : ""
            }
            id={styles.input}
          />
          {formik.touched.userPassword && formik.errors.userPassword && (
            <div>{formik.errors.userPassword}</div>
          )}
        </div>
        {/* 로그인 버튼 */}
        <div className={styles.buttonBox}>
          <button type="submit" className={styles.button}>
            로그인
          </button>
          <Link to="/regist" className={styles.button}>
            회원가입
          </Link>
        </div>
      </form>
    </div>
  );
}

export default LoginForm;
