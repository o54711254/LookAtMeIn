import { useEffect, useState } from "react";
import axiosApi from "../../../../api/axiosApi";
import StarRating from "../../../ReviewBoard/StarRating/StarResult";

function ReportedReview() {
  const [reviewList, setReviewList] = useState([]);
  useEffect(() => {
    axiosApi
      .get(`/reviewBoard/report/list`)
      .then((res) => {
        setReviewList(res.data);
      })
      .catch((error) => {
        console.log(
          "신고된 리뷰 게시판을 불러오는데 오류가 발생했습니다.",
          error
        );
      });
  });
  return (
    <div>
      {reviewList.length > 0 ? (
        <div>
          {reviewList.map((board, index) => {
            <li key={index}>
              <div>작성자: {board.customer_id}</div>
              <div>제목: {board.reviewBoard_title}</div>
              <div>조회수: {board.reviewBoard_cnt}</div>
              <div>작성날짜: {board.reviewBoard_regDate}</div>
              <div>
                <StarRating score={board.reviewBoard_score} />
              </div>
            </li>;
          })}
        </div>
      ) : (
        <div>시술 후기에 신고된 글이 없습니다.</div>
      )}
    </div>
  );
}
export default ReportedReview;
