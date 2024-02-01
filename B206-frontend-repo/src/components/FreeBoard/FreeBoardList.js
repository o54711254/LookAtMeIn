import React, { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import logo from "../../assets/lab_logo.png";
import s from "classnames";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axiosApi from "../../api/axiosApi";

function FreeBoardList() {
  const nevigate = useNavigate();
  const dispatch = useDispatch();

  const [freeboardList, setFreeBoardList] = useState([]);
  useEffect(() => {
    axiosApi
      .get(`api/freeBoardList`)
      .then((response) => {
        const sortedData = response.data.sort(
          (a, b) => new Date(b.time) - new Date(a.time)
        );
        setFreeBoardList(sortedData);
      })
      .catch((error) => {
        console.log("자유게시판 불러오기 에러: ", error);
      });
  }, []);

  return (
    <>
      <div>
        <h3>자유 게시판</h3>
        <p>다양한 정보를 자유롭게 공유하세요</p>
        <ul>
          {freeboardList.map((board, index) => {
            <li key={index}>
              {/* //아니면 그냥 인덱스로? */}
              <div>No. {board.free_board_seq}</div>
              <div>프로필 사진 : {board.customer_img}</div>
              <div>작성자 : {board.customer_info_name}</div>
              <div>제목: {board.free_board_title}</div>
              <div>댓글 수: {board.comment_cnt}</div>
              <div>조회수: {board.free_board_cnt}</div>
              <div>작성날짜: {board.free_board_regdate}</div>
            </li>;
          })}
        </ul>
        {/* 글작성 모달달기 */}
      </div>
    </>
  );
}
export default FreeBoardList;
