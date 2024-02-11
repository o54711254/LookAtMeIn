import React from "react";
import { Link } from "react-router-dom";
import * as yup from "yup";
import logo from "../../assets/logo.png";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { useFormik } from "formik";
import axiosApi from "../../api/axiosApi.js";
import { loginUser } from "../../redux/user.js";
import { setToken } from "../../redux/auth";
import styles from "./LoginForm.module.css";
// axios test 완료

const validationSchema = yup.object({
  userId: yup.string().required("아이디 입력해주세요."),
  userPassword: yup.string().required("패스워드를 입력해주세요."),
});

function LoginForm() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const formik = useFormik({
    initialValues: {
      userId: "",
      userPassword: "",
    },
    validationSchema: validationSchema,

    onSubmit: async (values) => {
      try {
        await axiosApi.post("/api/user/login", values).then((res) => {
          console.log(values);
          //res는 서버에서 받은 응답 객체
          if (res.status === 200) {
            //로그인 성공
            dispatch(
              loginUser({
                userSeq: res.data.userSeq, // 사용자 일련번호
                userId: values.userId, // 사용자 아이디
                userName: res.data.userName, // 사용자 이름
                userPassword: values.userPassword, // 사용자 비밀번호
                role: res.data.userType, // 역할 업데이트
              })
            );

            const accessToken = res.data.tokenInfo.accessToken;
            dispatch(setToken({ accessToken: accessToken }));
            window.alert("반갑습니다. 로그인이 완료되었습니다. ");
            navigate("/");
          } else {
            window.alert("아이디나 비밀번호를 다시 확인해 주세요.");
          }
        });
      } catch {
        window.alert("아이디나 비밀번호를 다시 확인해 주세요.");
      }
    },
  });

  return (
    <div className={styles.loginFormContainer}>
      <div className={styles.logo}>
        <img src={logo} alt="로고사진" className={styles.logoIcon} />
      </div>
      <form onSubmit={formik.handleSubmit} className={styles.submitBox}>
        <div className={styles.inputBox}>
          <div className={styles.inputTitle}>ID</div>
          <input
            name="userId"
            placeholder="아이디를 입력해주세요"
            value={formik.values.userId}
            onChange={formik.handleChange}
            type="text"
            className={formik.touched.id && formik.errors.id ? "error" : ""}
            id={styles.input}
          />
          {formik.touched.id && formik.errors.id && (
            <div>{formik.errors.id}</div>
          )}
        </div>
        <div className={styles.inputBox}>
          <div className={styles.inputTitle}>비밀번호</div>
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
