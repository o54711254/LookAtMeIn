// MyPage.js
import React from "react";
import { Routes, Route, NavLink } from "react-router-dom";
import WishList from "./WishList";
import MyPost from "./Post/MyPost";
import styles from "./MyPage.module.css";
import ReservationList from "./ReservationList";
import ConsultingList from "./ConsultingList";
import InfoUpdate from "./InfoUpdate";

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
            to="update"
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            정보수정
          </NavLink>
        </nav>
      </div>
      <div className={styles.content}>
        <Routes>
          <Route path="wish" element={<WishList />} />
          <Route path="mypost/*" element={<MyPost />} />
          <Route path="reserve" element={<ReservationList />} />
          <Route path="consult" element={<ConsultingList />} />
          <Route path="update" element={<InfoUpdate />} />
          {/* 이 부분에 추가적인 라우트를 설정할 수 있습니다. */}
        </Routes>
      </div>
    </div>
  );
}

export default MyPage;
