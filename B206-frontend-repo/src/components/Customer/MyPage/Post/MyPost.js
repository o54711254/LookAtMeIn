import { Link, Route, Routes } from "react-router-dom";
import PostList from "./PostList";
import ReviewList from "./ReviewList";
import ConsultRequestList from "./ConsultRequestList";
import styles from "./MyPost.module.css";

function MyPost() {
  return (
    <div className={styles.postContainer}>
      <div className={styles.postButton}>
        <Link to="free">자유게시판</Link>
        <Link to="review">후기게시판</Link>
        <Link to="request">상담요청게시판</Link>
      </div>
      <div className={styles.postContents}>
        <Routes>
          <Route path="free" element={<PostList />} />
          <Route path="review" element={<ReviewList />} />
          <Route path="request" element={<ConsultRequestList />} />
        </Routes>
      </div>
    </div>
  );
}
export default MyPost;
