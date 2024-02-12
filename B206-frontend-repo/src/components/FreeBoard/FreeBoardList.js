import React, { useEffect } from "react";
import { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import FreeBoardRegist from "./FreeBoardRegist.js";
import styles from "./FreeBoardList.module.css";
import profile from "../../assets/gun.png";

function FreeBoardList() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [freeboardList, setFreeBoardList] = useState([]);
  useEffect(() => {
    axiosApi
      .get(`api/freeBoard/freeBoardList`)
      .then((response) => {
        console.log(response.data);
        const sortedData = response.data.sort(
          (a, b) => new Date(b.freeboardSeq) - new Date(a.freeboardSeq)
        );
        setFreeBoardList(sortedData);
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
    <>
      <div className={styles.boardhead}>
        <div className={styles.headtitle}>
          <p>자유 게시판</p>
        </div>
        <div className={styles.headtext}>
          <p>다양한 정보를 자유롭게 공유하세요</p>
        </div>
      </div>
      <div>
        {freeboardList.map((board, index) => (
          <li
            key={index}
            onClick={() => goDetailPage(board.freeboardSeq)}
            className={styles.reviewItem}
          >
            <div className={styles.index}>No. {index + 1}</div>
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
        {/* 글작성 모달달기 */}
        <FreeBoardRegist />
      </div>
    </>
  );
}
export default FreeBoardList;
//리스트 잘 뜸!
