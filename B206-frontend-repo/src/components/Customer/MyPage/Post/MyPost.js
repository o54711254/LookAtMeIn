import { NavLink, Route, Routes } from "react-router-dom";
import PostList from "./PostList";
import ReviewList from "./ReviewList";
import ConsultRequestList from "./ConsultRequestList";
import styles from "./MyPost.module.css";

function MyPost() {
  return (
    <div className={styles.postContainer}>
      <div className={styles.postButton}>
        <NavLink to="free" className={styles.button}>
          자유게시판
        </NavLink>
        <NavLink to="review" className={styles.button}>
          후기게시판
        </NavLink>
        <NavLink to="request" className={styles.button}>
          상담요청게시판
        </NavLink>
      </div>
      <Routes>
        <Route path="free" element={<PostList />} />
        <Route path="review" element={<ReviewList />} />
        <Route path="request" element={<ConsultRequestList />} />
      </Routes>
    </div>
  );
}
export default MyPost;
