import { NavLink, Route, Routes } from "react-router-dom";
import Doctor from "./Doctor";
import ReservationList from "./ReservationList.js";
import ConsultingList from "./ConsultingList.js";
import styles from "./HospitalMyPage.module.css";

function HospitalMyPage() {
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
            to="doctor"
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            의사
          </NavLink>
          <NavLink
            to="reserve"
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            예약 상담 내역
          </NavLink>
          <NavLink
            to="consult"
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            상담 일정
          </NavLink>
        </nav>
      </div>
      <div className={styles.content}>
        <Routes>
          <Route path="doctor" element={<Doctor />} />
          <Route path="consult" element={<ConsultingList />} />
          <Route path="resereve" element={<ReservationList />} />
        </Routes>
      </div>
    </div>
  );
}
export default HospitalMyPage;
