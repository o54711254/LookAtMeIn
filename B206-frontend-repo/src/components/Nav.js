import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from "../assets/logo.png";
import styles from "./Nav.module.css";
import { useDispatch, useSelector } from "react-redux";
import { logoutUser } from "../redux/user";

function Nav() {
  const user = useSelector((state) => state.user);
  const userRoll = user.role;
  const isLogin = user.userSeq !== "";
  const navigate = useNavigate();
  const dispatch = useDispatch();

  // 메뉴 표시 상태를 제어하기 위한 상태 변수
  const [showMenu, setShowMenu] = useState(false);

  const handleLogout = () => {
    dispatch(logoutUser());
    navigate("/");
  };

  // "메뉴" 버튼 클릭 핸들러
  const toggleMenu = () => {
    setShowMenu(!showMenu);
  };

  //스크롤 내리면 네브바 크기 조정
  const [isScrolled, setIsScrolled] = useState(false);

  useEffect(() => {
    console.log(user.role);
    const handleScroll = () => {
      if (window.scrollY > 100) {
        // 100px 이상 스크롤 되었을 때
        setIsScrolled(true);
      } else {
        setIsScrolled(false);
      }
    };

    // 스크롤 이벤트 리스너 등록
    window.addEventListener("scroll", handleScroll);

    // 컴포넌트 언마운트 시 리스너 제거
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  //PLAY 토글 기능
  // 토글 상태를 위한 상태 변수
  const [showDropdown, setShowDropdown] = useState(false);
  // 드롭다운 메뉴를 토글하는 함수들
  // const toggleDropdownMenu = () => setShowDropdown((prevState) => !prevState);

  // 드롭다운을 보여주는 함수
  const showDropdownMenu = () => {
    setShowDropdown(true);
  };

  // 드롭다운을 숨기는 함수
  const hideDropdownMenu = () => {
    setShowDropdown(false);
  };

  return (
    <nav
      className={`${styles.navbar} ${isScrolled ? styles.navbarScrolled : ""}`}
    >
      <Link to="/" className={styles.nav0}>
        <img src={logo} alt="룩앳미인 로고" width="90px" />
      </Link>
      {/* 모바일 뷰에서 보일 메뉴 버튼 */}
      <button onClick={toggleMenu} className={styles.menuButton}>
        Menu
      </button>
      {/* 메뉴 내용 */}
      <div className={`${styles.navContainer} ${showMenu ? styles.show : ""}`}>
        <div className={styles.nav1}>
          <Link to="/hospitalList">병원 정보</Link>
          <Link to="/reviewList">시술 후기</Link>
          <Link to="/freeBoard/freeBoardList">자유게시판</Link>
          <div
            className={styles.dropdown}
            onMouseEnter={showDropdownMenu}
            onMouseLeave={hideDropdownMenu}
          >
            PLAY
            {showDropdown && (
              <div
                className={`${styles.dropdownContent} ${
                  showDropdown ? styles.show : ""
                }`}
              >
                <Link to="/face">얼굴(부가서비스 토글)</Link>
              </div>
            )}
          </div>
        </div>
        <div className={styles.nav2}>
          {!isLogin ? (
            <>
              <Link to="/login">로그인</Link>
              <Link to="/regist">회원가입</Link>
            </>
          ) : (
            <>
              <Link to="/requestboardlist">상담요청 게시판</Link>
              {/* 조건부 렌더링을 사용하여 다른 마이페이지 링크를 표시 */}
              {userRoll === "CUSTOMER" && <Link to="/mypage">마이페이지</Link>}
              {userRoll === "HOSPITAL" && (
                <Link to="/hospital-mypage">마이페이지</Link>
              )}
              {userRoll === "ADMIN" && (
                <Link to="/admin-mypage">마이페이지</Link>
              )}
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
