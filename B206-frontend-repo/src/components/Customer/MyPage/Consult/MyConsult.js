import { Link, Route, Routes } from "react-router-dom";
import ConsultingList from "./ConsultingList";
import ReservationList from "./ReservationList";

function MyConsult() {
  return (
    <div>
      <Link to="reserve">상담 예약</Link>
      <Link to="consulting">상담 내역</Link>
      <Routes>
        <Route path="reserve" element={<ReservationList />} />
        <Route path="consulting" element={<ConsultingList />} />
      </Routes>
    </div>
  );
}
export default MyConsult;
