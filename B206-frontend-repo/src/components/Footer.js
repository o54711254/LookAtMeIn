import React from "react";
import styles from "./Footer.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPhone, faEnvelope } from "@fortawesome/free-solid-svg-icons";
function Footer() {
  return (
    // <footer>
    <div className={styles.container}>
      <div className={styles.aboutus}>
        <h2>About Us</h2>
        <p>
          안녕하세요 저희는 WebRTC를 주제로 해 성형 상담 서비스 '룩앳미인'을
          제작한 B206팀 입니다{" "}
        </p>
      </div>
      <div className={styles.introduce}>
        <h2>Introduce BACKEND</h2>
        <ul>
          <li>
            <p>권준구</p>
          </li>
          <li>
            <p>김희수</p>
          </li>
          <li>
            <p>박하윤</p>
          </li>
        </ul>
      </div>
      <div className={styles.introduce}>
        <h2>Introduce FRONTEND</h2>
        <ul>
          <li>
            <p>오건영</p>
          </li>
          <li>
            <p>박지운</p>
          </li>
          <li>
            <p>심규리</p>
          </li>
        </ul>
      </div>
      <div className={styles.contact}>
        <h2>Contact Us</h2>
        <ul className={styles.info}>
          <li>
            <p>
              <FontAwesomeIcon icon={faPhone} /> +10 1234 5678
            </p>
          </li>
          <li>
            <p>
              <FontAwesomeIcon icon={faEnvelope} /> ssafy@B206ssafy.com
            </p>
          </li>
        </ul>
      </div>
      <div className={styles.copyrightText}>
        Copyrigh 2024 All ⓒ rights reserved
      </div>
    </div>

    // </footer>
  );
}

export default Footer;
