import { Routes, Route, BrowserRouter as Router } from "react-router-dom";
import review from "./components/ReviewBoard/ReviewList.js"


const Main = () => {
    return (
        <Router to="/review" element={review}></Router>
    );
};

export default Main;