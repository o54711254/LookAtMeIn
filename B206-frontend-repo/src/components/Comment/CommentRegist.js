import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";
import styles from "./CommentRegist.module.css";
function CommentRegist({ freeboardSeq }) {
  const [isRegist, setIsRegist] = useState(false);
  //user 리덕스에서 로그인한 사용자 userSeq 가져옴
  const userSeq = useSelector((state) => state.user.userSeq);

  const [comment, setComment] = useState({
    comment_content: "",
    freeboard_seq: freeboardSeq,
    user_seq: userSeq,
  });

  const startRegist = () => {
    setIsRegist(true);
  };

  const handleRegist = async () => {
    try {
      const response = await axiosApi.post(`api/comment/regist`, comment);
      window.location.reload();
    } catch (error) {
      console.log("댓글 작성 에러남 : ", error);
    }
  };

  const handleInputChange = (e) => {
    setComment({ ...comment, comment_content: e.target.value });
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      e.preventDefault(); // 엔터키 기본 동작 방지
      handleRegist();
    }
  };

  return (
    <div className={styles.registContainer}>
      {!isRegist ? (
        <input
          className={styles.inputBox}
          type="text"
          value={comment.comment_content}
          onFocus={startRegist}
          onChange={handleInputChange}
          placeholder="댓글을 입력하세요..."
        ></input>
      ) : (
        <div className={styles.activeBox}>
          <input
            className={styles.inputBox2}
            type="text"
            value={comment.comment_content}
            onChange={(e) =>
              setComment({ ...comment, comment_content: e.target.value })
            }
            onKeyDown={handleKeyDown}
          ></input>
          <button className={styles.button} onClick={handleRegist}>
            등록
          </button>
        </div>
      )}
    </div>
  );
}
export default CommentRegist;
