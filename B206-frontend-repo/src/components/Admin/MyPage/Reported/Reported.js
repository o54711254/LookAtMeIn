import { NavLink, Route, Routes } from "react-router-dom";
import ReportedFree from "./ReportedFree";
import ReportedReview from "./ReportedReview";
import styles from "./Reported.module.css";

function Reported() {
  return (
    <div className={styles.postContainer}>
      <div className={styles.postButton}>
        <NavLink
          to="free"
          className={({ isActive }) =>
            isActive ? `${styles.button} ${styles.active}` : styles.button
          }
        >
          자유게시판
        </NavLink>
        <NavLink
          to="review"
          className={({ isActive }) =>
            isActive ? `${styles.button} ${styles.active}` : styles.button
          }
        >
          후기게시판
        </NavLink>
      </div>
      <Routes>
        <Route path="free" element={<ReportedFree />} />
        <Route path="review" element={<ReportedReview />} />
      </Routes>
    </div>
  );
}
export default Reported;
