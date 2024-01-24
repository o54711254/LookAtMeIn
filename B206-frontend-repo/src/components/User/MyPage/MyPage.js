import { Routes, Link, Route } from "react-router-dom";
import MyWish from "./MyWish";
import MyPostList from "./MyPostList";
import ReviewList from "../../ReviewBoard/ReviewList";

function MyPage() {
  return (
    <div>
      <Link to={"/mypage/mywish"}>찜</Link>
      <Link to={"/mypage/mypost"}>내가 쓴 게시물</Link>
      <Link to={"/mypage/review"}>상담내역</Link>
      <Routes>
        <Route path="mywish" element={<MyWish />} />
        <Route path="mypost" element={<MyPostList />} />
        <Route path="review" element={<ReviewList />} />
      </Routes>
    </div>
  );
}
export default MyPage;
