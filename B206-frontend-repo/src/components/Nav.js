import React, { useState, useEffect } from 'react';
import logo_white from '../assets/gun.png';
import 'bootstrap/dist/css/bootstrap.min.css';
import "./Nav.module.css"

const Header = () => {
  const [loginStatus, setLoginStatus] = useState(false);

  useEffect(() => {
    if (sessionStorage.getItem('access-token') === null) {
      setLoginStatus(false);
    } else {
      setLoginStatus(true);
    }
  }, []);

  const logout = () => {
    sessionStorage.clear();
    setLoginStatus(false);
  };

  const navbarShrink = () => {
    const navbarCollapsible = document.body.querySelector('#mainNav');
    if (!navbarCollapsible) {
      return;
    }
    if (window.scrollY === 0) {
      navbarCollapsible.classList.remove('navbar-shrink');
    } else {
      navbarCollapsible.classList.add('navbar-shrink');
    }
  };

  useEffect(() => {
    navbarShrink();
    document.addEventListener('scroll', navbarShrink);

    const mainNav = document.body.querySelector('#mainNav');
    // if (mainNav) {
    //   new bootstrap.ScrollSpy(document.body, {
    //     target: '#mainNav',
    //     rootMargin: '0px 0px -40%',
    //   });
    // }

    const navbarToggler = document.body.querySelector('.navbar-toggler');
    const responsiveNavItems = Array.from(document.querySelectorAll('#navbarResponsive .nav-link'));
    responsiveNavItems.forEach(responsiveNavItem => {
      responsiveNavItem.addEventListener('click', () => {
        if (window.getComputedStyle(navbarToggler).display !== 'none') {
          navbarToggler.click();
        }
      });
    });
  }, []);

  return (
    <nav className="navbar navbar-expand-lg" id="mainNav">
      <div className="container">
        <a className="navbar-brand" to="#page-top"><img src={logo_white} alt="Logo" />LookAt美</a>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
          aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          Menu
          <i className="fas fa-bars ms-1"></i>
        </button>
        <div className="collapse navbar-collapse justify-content-between" id="navbarResponsive">
          <ul className="navbar-nav">
            <li className="nav-item"><a className="nav-link" href="#services">자유게시판</a></li>
            <li className="nav-item"><a className="nav-link" href="#portfolio">후기게시판</a></li>
            <li className="nav-item"><a className="nav-link" href="#about">병원</a></li>
            <li className="nav-item"><a className="nav-link" href="#team">부가서비스</a></li>
          </ul>
          <ul className="navbar-nav">
            {!loginStatus && <li className="nav-item"><a className="nav-link" href="/login">로그인</a></li>}
            {!loginStatus && <li className="nav-item"><a className="nav-link" href="/regist">회원가입</a></li>}
            {loginStatus && <li className="nav-item" onClick={logout}><a className="nav-link" href="/home">로그아웃</a></li>}
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Header;
