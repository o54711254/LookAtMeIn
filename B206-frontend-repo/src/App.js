import { BrowserRouter, Link, Routes, Route } from "react-router-dom";
import MyPage from "./components/Customer/MyPage/MyPage";
import FloatingChat from "./components/Chat/FloatingChat";
import LoginForm from "./components/Sign/LoginForm";
import Regist from "./components/Sign/Regist";
import UserRegistForm from "./components/Sign/UserRegistForm";
import HospitalRegistForm from "./components/Sign/HospitalRegistForm";
import Nav from "./components/Nav.js";
import Home from "./pages/Home";
import HospitalBoardList from "./components/HospitalBoard/HospitalList.js";
import ReviewBoardList from "./components/ReviewBoard/ReviewList";
import ReviewDetail from "./components/ReviewBoard/ReviewDetail";
import ReviewRegist from "./components/ReviewBoard/ReviewRegist";
import FreeBoardList from "./components/FreeBoard/FreeBoardList";
import RequestBoardList from "./components/RequestBoard/RequestBoardList.js";

import CustomerMyPage from "./pages/Mypage/CustomerMyPage.js";
import HosMyPage from "./pages/Mypage/HosMyPage.js";
import AdminMyPage from "./pages/Mypage/AdminMyPage.js";

import SearchList from "./components/Search/SearchList";
import VideoRoom from "./components/OpenVidu/streaming/OvVideo.js";
import SearchInput from "./components/Search/SearchInput";
import Footer from "./components/Footer";
import FacialAsy from "./components/FacialAsymmetry/FacialAsymmetry";

import "./styles/globals.css";

import FindPassword from "./components/Sign/FindPassword";
import ReserveModal from "./components/Modal/DateTimePickerModal";
import Report from "./components/Modal/ReviewReport.js";
import ReviewDelete from "./components/ReviewBoard/ReviewDelete.js";
import Favorite from "./components/HospitalBoard/HospitalWish.js";
import Questionnaire from "./components/Modal/Questionnaire.js";

import FreeBoardDetail from "./components/FreeBoard/FreeBoardDetail.js";
import Worldcup from "./components/WorldCup/Worldcup.js";
import ReviewUpdate from "./components/ReviewBoard/ReviewUpate.js";
import HospitalInfo from "./components/HospitalBoard/HospitalInfo.js";
import HospitalDetail from "./pages/HospitalDetail.js";

import Search from "./components/Search/SearchInput.js";
import Canvas from "./components/Canvas/canvas.js";

function App() {
  return (
    <div>
      <div className="app-container">
        <div className="content">
          <BrowserRouter>
            <Nav />
            <div className="con">
              {/* <Nav /> */}
              {/* <Link to="mypage">마이페이지</Link> */}
              <div>
                <Routes>
                  <Route path="/" element={<Home />} />

                  {/* 회원가입 관련 */}
                  <Route path="/login" element={<LoginForm />} />
                  <Route path="/findPassword" element={<FindPassword />} />
                  <Route
                    path="/regist/UserRegist"
                    element={<UserRegistForm />}
                  />
                  <Route path="/regist" element={<Regist />} />
                  <Route
                    path="/regist/HospitalRegist"
                    element={<HospitalRegistForm />}
                  />

                  {/* 마이페이지 */}
                  <Route path="/mypage/*" element={<CustomerMyPage />} />
                  <Route path="/hospital-mypage/*" element={<HosMyPage />} />
                  <Route path="/admin-mypage/*" element={<AdminMyPage />} />

                  {/* 리뷰 게시판 */}
                  <Route path="/reviewList" element={<ReviewBoardList />} />
                  <Route path="/reviewboard/*" element={<ReviewBoardList />} />
                  <Route
                    path="/reviewdetail/:reviewBoard_seq"
                    element={<ReviewDetail />}
                  />
                  <Route path="/reviewregist" element={<ReviewRegist />} />
                  <Route path="/reviewupdate" element={<ReviewUpdate />} />

                  {/*자유 게시판*/}
                  <Route path="/freeboard/*" element={<FreeBoardList />} />
                  <Route
                    path="/freeBoard/freeBoardList"
                    element={<FreeBoardList />}
                  />
                  <Route
                    path="/freeBoard/freeBoardList/:freeboardSeq"
                    element={<FreeBoardDetail />}
                  />

                  {/*상담요청 게시판*/}
                  <Route
                    path="/requestboardlist"
                    element={<RequestBoardList />}
                  />

                  {/*병원 게시판*/}
                  <Route path="/hospitalList" element={<HospitalBoardList />} />
                  {/* <Route
                    path="/hospital-info/detail/:hospital_seq"
                    element={<HospitalDetail />}
                  /> */}

                  {/*이상향 월드컵*/}
                  <Route path="/worldcup/*" element={<Worldcup />} />

                  <Route path="/face" element={<FacialAsy />} />

                  {/*검색*/}
                  <Route path="/search/*" element={<Search />} />
                  <Route path="/search/:query" element={<SearchList />} />
                  {/* <Route path="/meeting/*" element={<VideoRoom />} /> */}
                  <Route path="/reviewregist" element={<ReviewRegist/> }/>
                  <Route path="/canvas" element={<Canvas />} />
                </Routes>
              </div>
            </div>
          </BrowserRouter>
          <div>
            {/* <Report /> */}
            {/* <ReserveModal /> */}
            {/* <ReviewDelete /> */}
            {/* <Favorite /> */}
          </div>
          {/* <Questionnaire /> */}
          <FloatingChat />
        </div>
        <Footer />
      </div>
    </div>
  );
}

export default App;