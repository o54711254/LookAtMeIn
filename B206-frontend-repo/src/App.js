import { BrowserRouter, Link, Routes, Route } from "react-router-dom";
import MyPage from "./components/Customer/MyPage/MyPage";
import FloatingChat from "./components/Chat/FloatingChat";
import LoginForm from "./components/Sign/LoginForm";
import Regist from "./components/Sign/Regist";
import UserRegistForm from "./components/Sign/UserRegistForm";
import HospitalRegistForm from "./components/Sign/HospitalRegistForm";
import Nav from "./components/Nav.js";
import Home from "./components/Home.js";
import HospitalBoardList from "./components/HospitalBoard/HospitalList.js";
import ReviewBoardList from "./components/ReviewBoard/ReviewList";
import FreeBoardList from "./components/FreeBoard/FreeBoardList";
import RequestBoardList from "./components/RequestBoard/RequestBoardList.js";
import Coordinatormypage from "./components/Coordinator/MyPage/CoordinatorMyPage";
import HospitalMyPage from "./components/Hospital/MyPage/HospitalMyPage";
import AdminMyPage from "./components/Admin/MyPage/AdminMyPage";

import SearchList from "./components/Search/SearchList";
// import VideoRoom from "./components/OpenVidu/VideoRoom";
import SearchInput from "./components/Search/SearchInput";
import ChatApp from "./components/Chat/ChatApp";
import Footer from "./components/Footer";
import FacialAsy from "./components/FacialAsymmetry/FacialAsymmetry";

// import "./styles/globals.css";

import FindPassword from "./components/Sign/FindPassword";
import ReserveModal from "./components/Modal/DateTimePickerModal";

function App() {
  return (
    <div>
      <div className="app-container">
        <div className="content">
          <BrowserRouter>
            <Nav />
            <div style={{ marginTop: "10vh", height: "88vh" }}>
              <Routes>
                {/*Sign*/}
                <Route path="/" element={<Home />} />
                <Route path="/hospitalList" element={<HospitalBoardList />} />
                <Route path="/reviewList" element={<ReviewBoardList />} />
                <Route path="/freeboardList" element={<FreeBoardList />} />
                <Route path="/face" element={<FacialAsy />} />
                <Route
                  path="/requestboardlist"
                  element={<RequestBoardList />}
                />
                <Route path="/login" element={<LoginForm />} />
                <Route path="/regist" element={<Regist />} />
                <Route path="/regist/UserRegist" element={<UserRegistForm />} />
                <Route
                  path="/regist/HospitalRegist"
                  element={<HospitalRegistForm />}
                />
                <Route path="/findPassword" element={<FindPassword />} />

                {/*Customer
          마이페이지 내부에서 라우트 하면 될듯
          */}
                <Route path="/mypage/*" element={<MyPage />} />
                {/* Coordinator
          이하동문
          */}
                <Route
                  path="/coordinator-mypage/*"
                  element={<Coordinatormypage />}
                />
                {/* Hospital */}
                <Route path="/hospital-mypage/*" element={<HospitalMyPage />} />
                {/* Admin */}
                {/* <Route path="/admin-mypage/*" element={<AdminMyPage />} /> */}
                {/* ReviewBoard */}
                {/* <Route path="/reviewboard/*" element={<ReviewBoardList />} /> */}
                {/* FreeBoard */}
                {/* <Route path="/freeboard/*" element={<FreeBoardList />} /> */}
                {/* Search */}
                {/* <Route path="/search/*" element={<SearchList />} /> */}
                {/* meeting */}
                {/* <Route path="/meeting/*" element={<VideoRoom />} /> */}
                {/* <Route path="/search/:query" element={<SearchList />} /> */}
              </Routes>
            </div>
          </BrowserRouter>
          <div>
            <ReserveModal />
          </div>
          <FloatingChat />
        </div>
        <Footer />
      </div>
    </div>
  );
}

export default App;
