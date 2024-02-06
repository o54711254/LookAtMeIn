import { Link, Route, Routes } from "react-router-dom";
import Doctor from "./Doctor";
import Coordinator from "./Coordinator";

function HospitalMyPage() {
  return (
    <div>
      <Link to="doctor">의사</Link>
      <Link to="coordinator">코디네이터</Link>
      <Routes>
        <Route path="doctor" element={<Doctor />} />
        <Route path="coordinator" element={<Coordinator />} />
      </Routes>
    </div>
  );
}
export default HospitalMyPage;
