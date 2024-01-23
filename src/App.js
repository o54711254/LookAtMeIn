<<<<<<< HEAD
import { Routes, Route, BrowserRouter as Router } from "react-router-dom";
import review from "./components/ReviewBoard/ReviewList.js"
=======
import { BrowserRouter, Link, Routes, Route } from "react-router-dom";
import MyPage from "./components/User/MyPage/MyPage";
import ChatModal from "./components/Chat/ChatModal";
>>>>>>> c1eab733464b9d74e3a0122fd20e2b9aae9e56e5

function App() {
  return (
    <div>
<<<<<<< HEAD
      <Router to="/review" element={review}></Router>
=======
      <BrowserRouter>
        <Link to={"/mypage"}>마이페이지</Link>

        <Routes>
          <Route path="/mypage/*" element={<MyPage />} />
        </Routes>
      </BrowserRouter>
      <ChatModal />
>>>>>>> c1eab733464b9d74e3a0122fd20e2b9aae9e56e5
    </div>
  );
}

export default App;
