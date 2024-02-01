import React from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import logo from "../assets/lab_logo.png";
import styles from "./Nav.module.css";
import s from "classnames";
import { useDispatch, useSelector } from "react-redux";
import { logoutUser } from "../redux/user";
import { useNavigate } from "react-router-dom";

function Nav() {
  const user = useSelector((state) => state.user);
  const islogin = user.userSeq !== "";
  const navigate = useNavigate();

  const dispatch = useDispatch();
  const handleLogout = () => {
    dispatch(logoutUser);
    navigate("/");
  };

  return (
    <nav className={styles.navbar}>
      <Link style={{ textDecoration: "none" }} to="/" className={styles.nav0}>
        <img src={logo} alt="로고사진" width={"40px"} />
        <div>룩앳미인</div>
      </Link>
      <div className={styles.navContainer}>
        <div className={styles.nav1}>
          <div>
            <Link style={{ textDecoration: "none" }} to={"/hospitaldivst"}>
              병원 정보
            </Link>
          </div>
          <div>
            <Link style={{ textDecoration: "none" }} to="/reviewdivst">
              시술 후기
            </Link>
          </div>

          <div>
            <Link style={{ textDecoration: "none" }} to="/freeboarddivst">
              자유게시판
            </Link>
          </div>
          <div>
            <Link style={{ textDecoration: "none" }} to="/face">
              얼굴(부가서비스 토글)
            </Link>
          </div>
        </div>
        <div className={styles.nav2}>
          {!islogin ? (
            <>
              <div>
                <Link style={{ textDecoration: "none" }} to="/login">
                  로그인
                </Link>
              </div>
              <div>
                <Link style={{ textDecoration: "none" }} to="/regist">
                  회원가입
                </Link>
              </div>
            </>
          ) : (
            <>
              <div>
                <Link style={{ textDecoration: "none" }} to="/requestboardlist">
                  상담요청 게시판
                </Link>
              </div>
              <div>
                <Link style={{ textDecoration: "none" }} to="/mypage">
                  마이페이지
                </Link>
              </div>
              <div onClick={handleLogout} className={styles.cursor}>
                로그아웃
              </div>
            </>
          )}
        </div>
      </div>
    </nav>
  );
}

export default Nav;
