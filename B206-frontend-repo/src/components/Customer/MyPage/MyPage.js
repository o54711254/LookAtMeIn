// MyPage.js
import React from "react";
import { Routes, Route, NavLink } from "react-router-dom";
import WishList from "./WishList";
import MyPost from "./Post/MyPost";
import styles from "./MyPage.module.css";
import ReservationList from "./ReservationList";
import ConsultingList from "./ConsultingList";
import InfoUpdate from "./InfoUpdate";
import MyInfo from "./MyInfo";

function MyPage() {
  return (
    <div className={styles.mypageContainer}>
      <div className={styles.sidebar}>
        {/* <imgdd
          src="/path-to-your-profile-image.jpg"
          alt="프로필 이미지"
          className={styles.profileImage}
        /> */}
        <h3 className={styles.profileName}>고객님의 이름</h3>
        <nav className={styles.navigation}>
          <NavLink
            to="wish"
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            찜
          </NavLink>
          <NavLink
            to="mypost"
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            내 게시물
          </NavLink>
          <NavLink
            to="reserve"
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            예약 상담 목록
          </NavLink>
          <NavLink
            to="consult"
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            상담내역
          </NavLink>
          <NavLink
            to="info"
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            내정보
          </NavLink>
        </nav>
      </div>
      <div className={styles.content}>
        <Routes>
          <Route path="wish" element={<WishList />} />
          <Route path="mypost/*" element={<MyPost />} />
          <Route path="reserve" element={<ReservationList />} />
          <Route path="consult" element={<ConsultingList />} />
          <Route path="info" element={<MyInfo />} />
          <Route path="info/update" element={<InfoUpdate />} />
        </Routes>
      </div>
    </div>
  );
}

export default MyPage;
