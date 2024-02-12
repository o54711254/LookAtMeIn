// MyPage.js
import React from "react";
import { Routes, Route, NavLink } from "react-router-dom";
import styles from "./AdminMyPageForm.module.css";
import { useSelector } from "react-redux";
import reserve from "../../../assets/mypage/reserve.png";
import wish from "../../../assets/mypage/wish.png";
import HospitaList from "./HospitalList";
import UserList from "./UserList";
import Reported from "./Reported/Reported";

function AdminMyPageForm() {
  const user = useSelector((state) => state.user);
  return (
    <div className={styles.mypageContainer}>
      <NavLink to="info" className={styles.profile}>
        <div className={styles.profileImage} />
        <div className={styles.profileInfo}>
          <div className={styles.profileName}>{user.userName} 관리자</div>
          <div className={styles.profileEmail}>Administrator</div>
        </div>
      </NavLink>
      <div className={styles.sidebar}>
        <nav className={styles.navigation}>
          <div className={styles.column}>
            <img src={wish} className={styles.icon} />
            <NavLink
              to="hospital"
              className={({ isActive }) =>
                isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
              }
            >
              병원관리
            </NavLink>
          </div>
          <div className={styles.horizon}></div>
          <div className={styles.column}>
            <img src={reserve} alt="icon" className={styles.icon} />
            <NavLink
              to="user"
              className={({ isActive }) =>
                isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
              }
            >
              회원관리
            </NavLink>
          </div>
          <div className={styles.horizon}></div>
          <div className={styles.column}>
            <img src={reserve} alt="icon" className={styles.icon} />
            <NavLink
              to="reported"
              className={({ isActive }) =>
                isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
              }
            >
              신고관리
            </NavLink>
          </div>
        </nav>
      </div>
      <div className={styles.contents}>
        <Routes>
          <Route path="hospital" element={<HospitaList />} />
          <Route path="user" element={<UserList />} />
          <Route path="reported/*" element={<Reported />} />
        </Routes>
      </div>
    </div>
  );
}

export default AdminMyPageForm;
