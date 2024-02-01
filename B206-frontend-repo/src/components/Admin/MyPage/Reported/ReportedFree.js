import { useEffect, useState } from "react";
import axiosApi from "../../../../api/axiosApi";

function ReportedFree() {
  const [boardList, setBoardList] = useState([]);
  useEffect(() => {
    axiosApi
      .get(`/freeBoard/report/list`)
      .then((res) => {
        setBoardList(res.data);
      })
      .catch((error) => {
        console.log("신고된 자유게시판 목록을 불러오는데 실패했습니다.", error);
      });
  });
  return (
    <div>
      {boardList.length > 0 ? (
        <div>
          {boardList.map((board, index) => {
            <li key={index}>
              <div>No. {board.free_board_seq}</div>
              <div>프로필 사진 : {board.customer_img}</div>
              <div>작성자 : {board.customer_info_name}</div>
              <div>제목: {board.free_board_title}</div>
              <div>댓글 수: {board.comment_cnt}</div>
              <div>조회수: {board.free_board_cnt}</div>
              <div>작성날짜: {board.free_board_regdate}</div>
            </li>;
          })}
        </div>
      ) : (
        <div>자유게시판에 신고된 글이 없습니다.</div>
      )}
    </div>
  );
}
export default ReportedFree;
