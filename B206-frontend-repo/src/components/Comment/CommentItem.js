import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function CommentItem({ comment }) {
  const [isUpdate, setIsUpdate] = useState(false);

  //댓글 useState임
  const [c, setC] = useState({
    comment_content: comment.comment_content,
    comment_seq: comment.comment_seq,
    // user_seq: comment.user_seq, //근데 널값 넘어옴 왜?
  });

  const startUpdate = () => {
    setIsUpdate(true);
  };
  const handleUpdate = async () => {
    try {
      const response = await axiosApi.put(`api/comment/update`, c);
      // console.log(response.data);
      window.location.reload();
    } catch (error) {
      console.log("댓글 수정 에러남 : ", error);
    }
  };
  const handleDelete = () => {
    axios
      .put(`/api/comment/delete/${comment.comment_seq}`, {
        freeBoard_complain: true,
      })
      .then((response) => {
        console.log("댓글 삭제 됨 : ", response.data);
        window.location.reload();
      })
      .catch((error) => {
        console.error("댓글 삭제 에러 : ", error);
      });
  };

  return (
    <>
      <li>
        {!isUpdate ? (
          <p>댓글 내용 : {comment.comment_content}</p>
        ) : (
          <>
            <input
              type="text"
              value={c.comment_content}
              onChange={(e) => setC({ ...c, comment_content: e.target.value })}
            ></input>
            <button onClick={handleUpdate}>수정완료</button>
          </>
        )}

        <p>댓글 작성자 : {comment.customerId}</p>
        {/* <p>{comment.freeboard_seq}</p> */}
        <button onClick={startUpdate}>수정하기</button>
        <button onClick={handleDelete}>삭제하기</button>
      </li>
    </>
  );
}
export default CommentItem;
