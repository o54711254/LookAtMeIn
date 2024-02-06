import { Link, Route, Routes } from "react-router-dom";
import ReportedFree from "./ReportedFree";
import ReportedReview from "./ReportedReview";
import ReportedComment from "./ReportedComment";

function Reported() {
  return (
    <div>
      <Link to="free">자유게시판 신고 목록</Link>
      <Link to="review">시술후기 신고 목록</Link>
      <Link to="comment">댓글 신고 목록</Link>
      <Routes>
        <Route path="free" element={<ReportedFree />} />
        <Route path="review" element={<ReportedReview />} />
        <Route path="comment" element={<ReportedComment />} />
      </Routes>
    </div>
  );
}
export default Reported;
