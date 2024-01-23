import { BrowserRouter, Link, Routes, Route } from "react-router-dom";
import MyPage from "./components/User/MyPage/MyPage";
import ChatModal from "./components/Chat/ChatModal";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Link to={"/mypage"}>마이페이지</Link>
        <Routes>
          <Route path="/mypage/*" element={<MyPage />} />
        </Routes>
      </BrowserRouter>
      <ChatModal />
    </div>
  );
}

export default App;
