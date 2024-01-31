import { BrowserRouter, Link, Routes, Route } from "react-router-dom";
import MyPage from "./components/User/MyPage/MyPage";
import FloatingChat from "./components/Chat/FloatingChat";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Link to={"/mypage"}>마이페이지</Link>
        <Routes>
          <Route path="/mypage/*" element={<MyPage />} />
        </Routes>
      </BrowserRouter>
      <FloatingChat />
    </div>
  );
}

export default App;
