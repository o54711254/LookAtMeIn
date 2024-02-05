import { Link, Route, Routes } from "react-router-dom";
import WorldcupMan from "./WorldcupMan";
import WorldcupWoman from "./WorldcupWoman";

function Worldcup() {
  return (
    <div>
      <Link to="man">남성용</Link>
      <Link to="man">여성용</Link>
      <Routes>
        <Route path="man" element={<WorldcupMan />}></Route>
        <Route path="woman" element={<WorldcupWoman />}></Route>
      </Routes>
    </div>
  );
}
export default Worldcup;
