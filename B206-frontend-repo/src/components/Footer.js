import React from 'react';
import "./Footer.module.css"

function Footer() {
  return (
    <footer>
      <div className="inner">
        <div className="footer-message">
          강준규 권준구 최준호 오건영 김근형 임현승 이성현
        </div>
        <div className="footer-contact">컨택: dream@fun-coding.org</div>
        <div className="footer-copyright">Copyrigh 2020 All ⓒ rights reserved</div>
      </div>
    </footer>
  );
}

export default Footer;
