import React, { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import logo from "../../assets/lab_logo.png";
import s from "classnames";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axiosApi from "../../api/axiosApi";

function RequestBoardList() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  //더미데이터
  // const RequestBoardList = {
  //   customer_id: "user123",
  //   requestBoard_title: "테스트 리뷰",
  //   requestBoard_cnt: 100,
  //   requestBoard_regDate: "2024-01-28",
  //   requestBoard_score: 4,
  // };

  const [requestBordList, setRequestBoardList] = useState([]);
  useEffect(() => {
    axiosApi
      .get(`api/requestBoardList`)
      .then((response) => {
        // regDate를 사용하여 데이터를 최신순으로 정렬
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
      <div>
        <h3>상담 요청</h3>
        <p>
          여러 병원의 전문 코디네이터들이 화상상담을 활용하여 맞춤형 컨설팅을
          제공합니다.
        </p>
        <ul>
          {requestBordList.map((board, index) => (
            <li key={index}>
              <div>프로필 사진 : {board.customer_img}</div>
              <div>작성자 : {board.customer_id}</div>
              <div>제목: {board.requestBoard_title}</div>
              <div>해시태그: {board.requestBoard_hashTag}</div>
              <div>조회수: {board.requestBoard_cnt}</div>
              <div>작성날짜: {board.requestBoard_}</div>
            </li>
          ))}
        </ul>
      </div>
    </>
  );
}
export default RequestBoardList;
