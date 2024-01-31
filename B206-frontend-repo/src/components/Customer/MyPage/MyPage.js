import { Routes, Link, Route } from "react-router-dom";
import MyWish from "./MyWish";
import MyPost from "./Post/MyPost";
import MyConsult from "./Consult/MyConsult";

function MyPage() {
  return (
    <div>
      <Link to="mywish">찜</Link>
      <Link to="mypost">내 게시물</Link>
      <Link to="consult">상담</Link>
      <Routes>
        <Route path="mywish" element={<MyWish />} />
        <Route path="mypost/*" element={<MyPost />} />
        <Route path="consult/*" element={<MyConsult />} />
      </Routes>
    </div>
  );
}
export default MyPage;
