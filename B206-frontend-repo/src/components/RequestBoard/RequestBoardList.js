import React, { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import logo from "../../assets/logo.png";
import s from "classnames";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import styles from "./RequestBoardList.module.css";
import profile from "../../assets/gun.png";

function RequestBoardList() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [requestBordList, setRequestBoardList] = useState([]);
  useEffect(() => {
    axiosApi
      .get(`api/requestboard/read`)
      .then((response) => {
        console.log(response.data);
        const sortedData = response.data.sort(
          (a, b) => new Date(b.time) - new Date(a.time)
        );
        setRequestBoardList(sortedData);
      })
      .catch((error) => {
        console.error("상담요청 게시판 불러오기 에러 : :", error);
      });
  }, []);

  return (
    <>
      <div className={styles.boardhead}>
        <div className={styles.headtitle}>
          <p>상담 요청</p>
        </div>
        <div className={styles.headtext}>
          <p>
            여러 병원의 전문 코디네이터들이 화상상담을 활용하여 맞춤형 컨설팅을
            제공합니다.
          </p>
        </div>
        <div>
          {requestBordList.map((board, index) => (
            <li key={index} className={styles.reviewItem}>
              {/* <div>프로필 사진 : {board.customer_img}</div> 밑에 img태그 지우고 프사 되면 이걸로 바꾸자..*/}
              <div>
                <img src={profile} alt="프로필" className={styles.profile} />
              </div>
              <div className={styles.writer}>
                <div>{board.customer_id}</div>
              </div>
              {/* <div>작성자 : {board.customer_id}</div> */}
              <div className={styles.title}>
                <div>{board.requestBoard_title}</div>
              </div>
              {/* <div>제목: {board.requestBoard_title}</div> */}
              <div>해시태그: {board.requestBoard_hashTag}</div>
              <div>조회수: {board.requestBoard_cnt}</div>
              <div>작성날짜: {board.requestBoard_}</div>
            </li>
          ))}
        </div>
      </div>
    </>
  );
}
export default RequestBoardList;
