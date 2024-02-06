import { useEffect, useState } from "react";
import axiosAPi from "../../../../api/axiosApi";
import { useSelector } from "react-redux";
import { styles } from "./PostList.module.css";

function PostList() {
  const [postList, setPostList] = useState([]);
  const user = useSelector((state) => state.user);
  useEffect(() => {
    axiosAPi
      .get(`/api/mypage/free/${user.userSeq}`)
      .then((res) => {
        console.log(res.data);
        setPostList(res.data);
      })
      .catch((error) => {
        console.log("데이터를 가져오는데 실패했습니다.", error);
      });
  }, []);
  return (
    <div>
      {postList.length > 0 ? (
        <div>
          {postList.map((post) => {
            <div>
              <div>{post.freeboardSeq}</div>
              <div>작성자 : {post.userId}</div>
              <div>제목 : {post.freeboardTitle}</div>
              <div>내용 : {post.freeboardContent}</div>
              <div>조회수 : {post.freeboardCnt}</div>
              <div>작성날짜 : {post.freeboardRegisterdate}</div>
            </div>;
          })}
        </div>
      ) : (
        <div>작성한 자유게시판 글 목록이 비어있습니다.</div>
      )}
    </div>
  );
}
export default PostList;
