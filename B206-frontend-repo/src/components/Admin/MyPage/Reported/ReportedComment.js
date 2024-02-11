import { useEffect, useState } from "react";
import axiosApi from "../../../../api/axiosApi";

function ReportedComment() {
  const [comment, setComment] = useState([]);
  useEffect(() => {
    axiosApi
      .get(`/comment/report/list`)
      .then((res) => {
        setComment(res.data);
      })
      .catch((error) => {
        console.log("신고된 댓글 목록을 가져오는데 실패했습니다.", error);
      });
  });
  return (
    <div>
      {comment.length > 0 ? (
        <div>
          {comment.map((data, index) => (
            <li key={index}>
              <div>{data.name}</div>
              <div>{data.content}</div>
            </li>
          ))}
        </div>
      ) : (
        <div>신고된 댓글이 없습니다.</div>
      )}
    </div>
  );
}
export default ReportedComment;
