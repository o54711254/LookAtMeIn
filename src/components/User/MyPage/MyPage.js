import { Routes, Link, Route } from "react-router-dom";
import MyWish from "./MyWish";
import MyPostList from "./MyPostList";

function MyPage() {
  return (
    <div>
      <Link to={"/mypage/mywish"}>찜</Link>
      <Link to={"/mypage/mypost"}>내가 쓴 게시물</Link>
      <Routes>
        <Route path="mywish" element={<MyWish />} />
        <Route path="mypost" element={<MyPostList />} />
      </Routes>
    </div>
  );
}
export default MyPage;
