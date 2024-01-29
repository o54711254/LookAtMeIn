import { BrowserRouter, Link, Routes, Route } from "react-router-dom";
import MyPage from "./components/Customer/MyPage/MyPage";
import FloatingChat from "./components/Chat/FloatingChat";
import Login from "./components/Sign/Login";
import LoginForm from "./components/Sign/LoginForm";
import Regist from "./components/Sign/Regist";
import UserRegistForm from "./components/Sign/UserRegistForm";
import HospitalRegistForm from "./components/Sign/HospitalRegistForm";
import Coordinatormypage from "./components/Coordinator/MyPage/CoordinatorMyPage";
import HospitalMyPage from "./components/Hospital/MyPage/HospitalMyPage";
import AdminMyPage from "./components/Admin/MyPage/AdminMyPage";
import ReviewBoardList from "./components/ReviewBoard/ReviewList";
import FreeBoardList from "./components/FreeBoard/FreeBoardList";
import SearchList from "./components/Search/SearchList";
import VideoRoom from "./components/OpenVidu/VideoRoom";
import SearchInput from "./components/Search/SearchInput";
import ChatApp from "./components/Chat/ChatApp";


function App() {
  return (
    <div>
      <BrowserRouter>
        <Link to={"/mypage"}>마이페이지d</Link>
        <SearchInput />
        <Routes>
          {/*Sign*/}
          <Route path="/login" element={<Login />} />
          <Route path="/login/*" element={<LoginForm />} />
          <Route path="/regist" element={<Regist />} />
          <Route path="/regist/*" element={<UserRegistForm />} />
          <Route path="/regist/*" element={<HospitalRegistForm />} />
          {/*Customer
          마이페이지 내부에서 라우트 하면 될듯
          */}
          <Route path="/mypage/*" element={<MyPage />} />
          {/*Coordinator
          이하동문
          */}
          <Route path="/coordinator-mypage/*" element={<Coordinatormypage />} />
          {/* Hospital */}
          <Route path="/hospital-mypage/*" element={<HospitalMyPage />} />
          {/* Admin */}
          <Route path="/admin-mypage/*" element={<AdminMyPage />} />
          {/* ReviewBoard */}
          <Route path="/reviewboard/*" element={<ReviewBoardList />} />
          {/* FreeBoard */}
          <Route path="/freeboard/*" element={<FreeBoardList />} />
          {/* Search */}
          <Route path="/search/*" element={<SearchList />} />
          {/* meeting */}
          <Route path="/meeting/*" element={<VideoRoom />} />
          <Route path="/search/:query" element={<SearchList />} />
        </Routes>
      </BrowserRouter>
      <FloatingChat />
    </div>
  );
}

export default App;