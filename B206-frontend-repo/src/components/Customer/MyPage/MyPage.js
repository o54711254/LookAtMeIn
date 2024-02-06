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
import { useSelector } from "react-redux";
import beauty from "../../../assets/mypage/beauty.png";
import post from "../../../assets/mypage/post.png";
import consult from "../../../assets/mypage/consult.png";
import reserve from "../../../assets/mypage/reserve.png";
import wish from "../../../assets/mypage/wish.png";

function MyPage() {
  const user = useSelector((state) => state.user);
  return (
    <div className={styles.mypageContainer}>
      <NavLink to="info" className={styles.profile}>
        <div className={styles.profileImage}></div>
        <div className={styles.profileInfo}>
          <div className={styles.profileName}>{user.userName}</div>
          <div className={styles.profileEmail}>asdfas@asdfsda.com</div>
        </div>
      </NavLink>
      <div className={styles.sidebar}>
        <nav className={styles.navigation}>
          <div className={styles.column}>
            <img src={wish} className={styles.icon} />
            <NavLink
              to="wish"
              className={({ isActive }) =>
                isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
              }
            >
              찜
            </NavLink>
          </div>
          <div className={styles.horizon}></div>
          <div className={styles.column}>
            <img src={reserve} alt="icon" className={styles.icon} />
            <NavLink
              to="reserve"
              className={({ isActive }) =>
                isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
              }
            >
              상담 예약
            </NavLink>
          </div>
          <div className={styles.horizon}></div>
          <div className={styles.column}>
            <img src={consult} alt="icon" className={styles.icon} />
            <NavLink
              to="consult"
              className={({ isActive }) =>
                isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
              }
            >
              상담 내역
            </NavLink>
          </div>
          <div className={styles.horizon}></div>
          <div className={styles.column}>
            <img src={post} alt="icon" className={styles.icon} />
            <NavLink
              to="mypost"
              className={({ isActive }) =>
                isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
              }
            >
              내 게시물
            </NavLink>
          </div>
          <div className={styles.horizon}></div>
          <div className={styles.column}>
            <img src={beauty} alt="icon" className={styles.icon} />
            <NavLink
              to="mypost"
              className={({ isActive }) =>
                isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
              }
            >
              나의 이상향
            </NavLink>
          </div>
          <div className={styles.horizon}></div>
        </nav>
      </div>
      <div className={styles.contents}>
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
