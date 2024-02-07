import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import styles from "./PostList.module.css";
import { useNavigate } from "react-router-dom";
import axiosApi from "../../../../api/axiosApi";
import profile from "../../../../assets/gun.png";

function PostList() {
  const [postList, setPostList] = useState([]);
  const user = useSelector((state) => state.user);
  const navigate = useNavigate();

  useEffect(() => {
    axiosApi
      .get(`api/mypage/free/${user.userSeq}`)
      .then((response) => {
        console.log(response.data);
        const sortedData = response.data.sort(
          (a, b) => new Date(b.freeboardSeq) - new Date(a.freeboardSeq)
        );
        setPostList(sortedData);
      })
      .catch((error) => {
        console.log("자유게시판 불러오기 에러: ", error);
      });
  }, []);

  const goDetailPage = (freeboardSeq) => {
    if (freeboardSeq) {
      navigate(`/freeBoard/freeBoardList/${freeboardSeq}`);
    } else {
      console.log("freeBoardSeq is undefined");
    }
  };

  return (
    <div>
      <div>작성한 글 갯수 : {postList.length}</div>
      {postList.length > 0 ? (
        <div>
          {postList.map((board, index) => (
            <li
              key={index}
              onClick={() => goDetailPage(board.freeboardSeq)}
              className={styles.reviewItem}
            >
              <div>No. {index}</div>
              <div>
                <img src={profile} alt="프로필" className={styles.profile} />
              </div>
              {/* <div>No. {index + 1}</div> */}
              <div className={styles.writer}>
                <div>{board.userId}</div>
              </div>
              <div className={styles.title}>
                <div>{board.freeboardTitle}</div>
              </div>

              {/* <div>댓글 수: {board.comment_cnt}</div> */}
              {/* <div className={styles.cnt}>{board.freeboardCnt}</div> */}
              <div className={styles.date}>{board.freeboardRegisterdate}</div>
            </li>
          ))}
        </div>
      ) : (
        <div>작성한 자유게시판 글 목록이 비어있습니다.</div>
      )}
    </div>
  );
}
export default PostList;
