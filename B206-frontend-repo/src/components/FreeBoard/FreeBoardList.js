import React, { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import logo from "../../assets/lab_logo.png";
import s from "classnames";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import FreeBoardRegist from "./FreeBoardRegist.js";

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

  // useEffect(() => {
  //   console.log(freeboardList); // 상태가 업데이트되고 나서 로그를 출력
  // }, [freeboardList]);

  const goDetailPage = (freeboardSeq) => {
    if (freeboardSeq) {
      navigate(`/freeBoard/freeBoardList/${freeboardSeq}`);
    } else {
      console.log("freeBoardSeq is undefined");
    }
  };
  return (
    <>
      <div>
        <h3>자유 게시판</h3>
        <p>다양한 정보를 자유롭게 공유하세요</p>
        <ul>
          {freeboardList.map((board, index) => (
            <li key={index} onClick={() => goDetailPage(board.freeboardSeq)}>
              {/* //아니면 그냥 인덱스로? */}
              <div>No. {index + 1}</div>
              {/* <div>프로필 사진 : {board.customer_img}</div> */}
              <div>작성자 : {board.userId}</div>
              <div>제목: {board.freeboardTitle}</div>
              {/* <div>댓글 수: {board.comment_cnt}</div> */}
              <div>조회수: {board.freeboardCnt}</div>
              <div>작성날짜: {board.freeboardRegisterdate}</div>
            </li>
          ))}
        </ul>
        {/* 글작성 모달달기 */}
        <FreeBoardRegist />
      </div>
    </>
  );
}
export default FreeBoardList;
//리스트 잘 뜸!
