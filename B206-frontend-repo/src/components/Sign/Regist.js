import React from "react";
import { useState } from "react";
import { Routes, Route, Link } from "react-router-dom";
import UserRegistForm from "../../components/Sign/UserRegistForm";
import HospitalRegistForm from "../../components/Sign/HospitalRegistForm";
import styles from "./Regist.module.css";
import { Button } from "@mui/material";
import logo from "../../assets/logo.png";

// import { BrowserRouter, Routes, Route } from "react-router-dom";

function Regist() {
  const [isAgreed, setIsAgreed] = useState(false);
  //체크박스 상태 변경 핸들러
  const handleCheckbox = (e) => {
    setIsAgreed(e.target.checked);
  };

  return (
    <div className={styles.container}>
      <img src={logo} alt="로고사진" className={styles.logo} />
      <div className={styles.check}>
        <label>
          개인정보 수집 및 이용 동의<span className={styles.agree}>(필수)</span>
          <input type="checkbox" checked={isAgreed} onChange={handleCheckbox} />
        </label>
      </div>
      <div className={styles.agreementSection}>
        <p>
          개인정보보호에 따라 룩앳미인에 회원가입 신청하시는 분께 수집하는
          개인정보의 항목, 개인정보의 수집 및 이용목적을 안내 드리오니 자세히
          읽은 후 동의하여 주시기 바랍니다.
          <br />
          <br />
          1. 수집하는 개인정보 이용자는 회원가입을 하지 않아도 정보 검색
          대부분의 룩앳미인 서비스를 회원과 동일하게 이용할 수 있습니다.
          회원가입을 할 경우, 룩앳미인은 서비스 이용을 위해 필요한 최소한의
          개인정보를 수집합니다. 회원가입 시점에 룩앳미인이 이용자로부터
          수집하는 개인정보는 아래와 같습니다.
          <br />
          <br />
          - 회원 가입 시 필수항목으로 아이디, 비밀번호, 이름, 생년월일, 성별,
          휴대전화번호를, 이메일주소를 수집합니다. 서비스 이용 과정에서 IP 주소,
          쿠키, 서비스 이용 기록, 기기정보, 위치정보가 생성되어 수집될 수
          있습니다. 또한 이미지가 수집될 수 있습니다.
          <br />
          <br /> 서비스 이용 과정에서 위치정보가 수집될 수 있습니다. 이와 같이
          수집된 정보는 개인정보와의 연계 여부 등에 따라 개인정보에 해당할 수
          있고, 개인정보에 해당하지 않을 수도 있습니다. 생성정보 수집에 대한
          추가 설명
          <br />
          <br />
          - IP(Internet Protocol) 주소란? IP 주소는 인터넷 망 사업자가 인터넷에
          접속하는 이용자의 PC 등 기기에 부여하는 온라인 주소정보 입니다. IP
          주소가 개인정보에 해당하는지 여부에 대해서는 각국마다 매우 다양한
          견해가 있습니다.
          <br />
          <br />
          - 서비스 이용기록이란? 룩앳미인 접속 일시, 이용한 서비스 목록 및
          서비스 이용 과정에서 발생하는 정상 또는 비정상 로그 일체,메일 수발신
          과정에서 기록되는 이메일주소, 친구 초대하기 또는 선물하기 등에서
          입력하는 휴대전화번호, 스마트스토어 판매자와 구매자간
          상담내역(룩앳미인톡톡 및 상품 Q&A 게시글) 등을 의미합니다.
          <br />
          <br />
          - 쿠키(cookie)란? 쿠키는 이용자가 웹사이트를 접속할 때에 해당
          웹사이트에서 이용자의 웹브라우저를 통해 이용자의 PC에 저장하는 매우
          작은 크기의 텍스트 파일입니다.
          <br />
          <br /> 이후 이용자가 다시 웹사이트를 방문할 경우 웹사이트 서버는
          이용자 PC에 저장된 쿠키의 내용을 읽어 이용자가 설정한 서비스 이용
          환경을 유지하여 편리한 인터넷 서비스 이용을 가능케 합니다.
          <br />
          <br /> 또한 방문한 서비스 정보, 서비스 접속 시간 및 빈도, 서비스 이용
          과정에서 생성된 또는 제공(입력)한 정보 등을 분석하여 이용자의 취향과
          관심에 특화된 서비스(광고 포함)를 제공할 수 있습니다.
          <br />
          <br /> 이용자는 쿠키에 대한 선택권을 가지고 있으며, 웹브라우저에서
          옵션을 설정함으로써 모든 쿠키를 허용하거나, 쿠키가 저장될 때마다
          확인을 거치거나, 아니면 모든 쿠키의 저장을 거부할 수도 있습니다.
          <br />
          <br /> 다만, 쿠키의 저장을 거부할 경우에는 로그인이 필요한 룩앳미인
          일부 서비스의 이용에 불편이 있을 수 있습니다. 쿠키에 관한 자세한
          내용(룩앳미인 프라이버시 센터) 알아보기
          <br />
          <br />
          2. 수집한 개인정보의 이용 룩앳미인 및 룩앳미인 관련 제반 서비스(모바일
          웹/앱 포함)의 회원관리, 서비스 개발・제공 및 향상, 안전한 인터넷
          이용환경 구축 등 아래의 목적으로만 개인정보를 이용합니다.
          <br />
          <br />
          - 보안, 프라이버시, 안전 측면에서 이용자가 안심하고 이용할 수 있는
          서비스 이용환경 구축을 위해 개인정보를 이용합니다. 수집하는 데이터는
          3년동안 관리하고 완전 삭제 합니다.
          <br />
          <br />
          3. 개인정보 수집 및 이용 동의를 거부할 권리 이용자는 개인정보의 수집
          및 이용 동의를 거부할 권리가 있습니다. 회원가입 시 수집하는 최소한의
          개인정보, 즉, 필수 항목에 대한 수집 및 이용 동의를 거부하실 경우,
          회원가입이 어려울 수 있습니다.
          <br />
          <br />
        </p>
      </div>
      <div className={styles.registBox}>
        {isAgreed && (
          <>
            <Link to="/regist/UserRegist" className={styles.select}>
              고객 회원가입
            </Link>
            <Link to="/regist/HospitalRegist" className={styles.select}>
              병원 회원가입
            </Link>
          </>
        )}
      </div>
      {/* <Routes>
        <Route path="/UserRegist" element={<UserRegistForm />}></Route>
        <Route path="/HospitalRegist" element={<HospitalRegistForm />}></Route>
      </Routes> */}
    </div>
  );
}
export default Regist;
