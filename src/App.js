import { Routes, Route, BrowserRouter as Router } from "react-router-dom";
import review from "./components/ReviewBoard/ReviewList.js"

function App() {
  return (
    <div>
      <Router to="/review" element={review}></Router>
    </div>
  );
}

export default App;
