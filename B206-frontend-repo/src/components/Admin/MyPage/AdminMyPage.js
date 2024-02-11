import { Link, Route, Routes } from "react-router-dom";
import HospitalList from "./HospitalList";
import Reported from "./Reported/Reported";
function AdminMyPageForm() {
  return (
    <div>
      <Link to="hospital">병원 관리</Link>
      <Link to="report">신고 관리</Link>
      <Routes>
        <Route path="hospital/*" element={<HospitalList />} />
        <Route path="report/*" element={<Reported />} />
      </Routes>
    </div>
  );
}
export default AdminMyPageForm;
