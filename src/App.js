import { BrowserRouter, Link, Routes, Route } from "react-router-dom";
import MyPage from "./components/User/MyPage/MyPage";
import MyWish from "./components/User/MyPage/MyWish";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Link to={"/mypage"}>마이페이지</Link>
        <Link to={"/mywish"}>찜</Link>
        <Routes>
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/mywish" element={<MyWish />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
