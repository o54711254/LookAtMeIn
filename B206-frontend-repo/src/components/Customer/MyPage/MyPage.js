import { Routes, Link, Route } from "react-router-dom";
import WishList from "./WishList";
import MyPost from "./Post/MyPost";
import MyConsult from "./Consult/MyConsult";

function MyPage() {
  return (
    <div>
      <Link to="wish">찜</Link>
      <Link to="mypost">내 게시물</Link>
      <Link to="consult">상담</Link>
      <div>나의 이상향</div>
      <div>정보 수정</div>
      <Routes>
        <Route path="wish" element={<WishList />} />
        <Route path="mypost/*" element={<MyPost />} />
        <Route path="consult/*" element={<MyConsult />} />
      </Routes>
    </div>
  );
}
export default MyPage;
