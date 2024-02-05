import { useEffect, useState } from "react";
import axiosAPi from "../../../../api/axiosApi";

function PostList() {
  const [postList, setPostList] = useState([]);
  useEffect(() => {
    axiosAPi
      .get(`/customer/freeBoardList/{user_seq}`)
      .then((res) => {
        setPostList(res.data);
      })
      .catch((error) => {
        console.log("데이터를 가져오는데 실패했습니다.", error);
      });
  });
  return (
    <div>
      {postList.length > 0 ? (
        <ul>
          {postList.map((post, index) => {
            <li key={index}>
              <div>{post.freeboardSeq}</div>
              <div>작성자 : {post.userId}</div>
              <div>제목 : {post.freeboardTitle}</div>
              <div>내용 : {post.freeboardContent}</div>
              <div>조회수 : {post.freeboardCnt}</div>
              <div>작성날짜 : {post.freeboardRegisterdate}</div>
            </li>;
          })}
        </ul>
      ) : (
        <div>작성한 자유게시판 글 목록이 비어있습니다.</div>
      )}
    </div>
  );
}
export default PostList;
