import { Link, Route, Routes } from "react-router-dom";
import PostList from "./PostList";
import ReviewList from "./ReviewList";
import ConsultRequestList from "./ConsultRequestList";

function MyPost() {
  return (
    <div>
      <Link to="free">자유게시판</Link>
      <Link to="review">후기게시판</Link>
      <Link to="request">상담요청게시판</Link>
      <Routes>
        <Route path="free" element={<PostList />} />
        <Route path="review" element={<ReviewList />} />
        <Route path="request" element={<ConsultRequestList />} />
      </Routes>
    </div>
  );
}
export default MyPost;
