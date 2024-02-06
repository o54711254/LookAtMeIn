// MyPage.js
import React from "react";
import { Routes, Route, NavLink } from "react-router-dom";
import WishList from "./WishList";
import MyPost from "./Post/MyPost";
import MyInfo from "./MyInfo";
import InfoUpdate from "./InfoUpdate";
import ConsultingList from "./ConsultingList";
import ReservationList from "./ReservationList";
import styles from "./MyPage.module.css";

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
            to="info"
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            내 정보
          </NavLink>
          <NavLink
            to="wish"
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            찜
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
            상담 내역
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
            나의 이상향
          </NavLink>
        </nav>
      </div>
      <div className={styles.content}>
        <Routes>
          <Route path="info" element={<MyInfo />} />
          <Route path="info/update" element={<InfoUpdate />} />
          <Route path="wish" element={<WishList />} />
          <Route path="mypost/*" element={<MyPost />} />
          <Route path="consult" element={<ConsultingList />} />
          <Route path="reserve" element={<ReservationList />} />
        </Routes>
      </div>
    </div>
  );
}

export default MyPage;
