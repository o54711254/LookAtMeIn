import React, { useEffect } from "react";
import { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import RequestBoardRegist from "./RequestBoardRegist";
import axiosApi from "../../api/axiosApi";
import styles from "./RequestBoardList.module.css";
import profile from "../../assets/gun.png";

function RequestBoardList() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [requestBoardList, setRequestBoardList] = useState([]);
  useEffect(() => {
    axiosApi
      .get(`api/requestboard/read`)
      .then((response) => {
        console.log(response.data);
        const sortedData = response.data.sort(
          (a, b) => new Date(b.requestboardSeq) - new Date(a.requestboardSeq)
        );
        setRequestBoardList(sortedData);
      })
      .catch((error) => {
        console.log("상담요청게시판 불러오기 에러: ", error);
      });
  }, []);

  const goDetailPage = (requestboardSeq) => {
    if (requestboardSeq) {
      navigate(`/requestBoard/requestBoardList/${requestboardSeq}`);
    } else {
      console.log("requestBoardSeq is undefined");
    }
  };
  return (
    <>
      <div className={styles.boardhead}>
        <div className={styles.headtitle}>
          <p>상담요청 게시판</p>
        </div>
        <div className={styles.headtext}>
          <p>전문가에게 상담을 요청하세요</p>
        </div>
      </div>
      <div>
        {requestBoardList.map((board, index) => (
          <li
            key={index}
            onClick={() => goDetailPage(board.seq)}
            className={styles.reviewItem}
          >
            <div className={styles.index}>No. {index + 1}</div>
            <div>
              <img src={profile} alt="프로필" className={styles.profile} />
            </div>
            {/* <div>No. {index + 1}</div> */}
            <div className={styles.writer}>
              <div>{board.userName}</div>
            </div>
            <div className={styles.title}>
              <div>{board.title}</div>
            </div>

            {/* <div>댓글 수: {board.comment_cnt}</div> */}
            {/* <div className={styles.cnt}>{board.freeboardCnt}</div> */}
            <div className={styles.date}>{board.regDate}</div>
          </li>
        ))}
        {/* 글작성 모달달기 */}
        <RequestBoardRegist />
      </div>
    </>
  );
}
export default RequestBoardList;
//리스트 잘 뜸!
