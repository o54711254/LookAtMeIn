import { Link, Route, Routes } from "react-router-dom";
import Consulting from "./Consulting";
import Reservation from "./Reservation";

function CoordinatorMyPage() {
  return (
    <div>
      <Link to={"consult"}>상담내역</Link>
      <Link to={"reserve"}>상담예약내역</Link>
      <Routes>
        <Route path="consult" element={<Consulting />} />
        <Route path="reserve" element={<Reservation />} />
      </Routes>
    </div>
  );
}
export default CoordinatorMyPage;
