import { Routes, Route, BrowserRouter as Router } from "react-router-dom";
import MyPage from "./components/User/MyPage/MyPage";
import Login from "./components/Sign/Login";
import Regist from "./components/Sign/Regist";

function App() {
  return (
    <Router>
      <Routes>
        {/* <Route path="/" element={<Home />} /> */}
        {/* <Route path="chatbot" element={<ChatBot />} /> */}

        <Route path="mypage" element={<MyPage />} />

        <Route path="login" element={<Login />} />

        <Route path="regist" element={<Regist />} />
      </Routes>
    </Router>
  );
}

export default App;
