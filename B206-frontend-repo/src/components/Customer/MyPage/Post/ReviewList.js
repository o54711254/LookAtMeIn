import { useEffect, useState } from "react";
import axiosAPi from "../../../../api/axiosApi";
import StarRating from "../../../ReviewBoard/StarRating/StarResult";
import { useSelector } from "react-redux";

function ReviewList() {
  const [reviewList, setReviewList] = useState([]);
  const user = useSelector((state) => state.user);
  const auth = useSelector((state) => state.auth);
  useEffect(() => {
    console.log(user);
    console.log(auth);
    axiosAPi
      .get(`/reviewBoard/list/${user.userSeq}`)
      .then((res) => {
        setReviewList(res.data);
      })
      .catch((error) => {
        console.log("데이터를 가져오는데 실패했습니다.", error);
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
        <div>작성한 시술 후기가 없습니다.</div>
      )}
    </div>
  );
}
export default ReviewList;
