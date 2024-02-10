import React, { useState } from "react";
import axiosApi from "../../api/axiosApi";
import styles from "./CommentItem.module.css";
import profile from "../../assets/man/유승호.jpg";

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
    axiosApi
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
    <div className={styles.commentContainer}>
      <div className={styles.top}>
        <img src={profile} className={styles.profileImg} />
        <div className={styles.topRight}>
          <div className={styles.userId}>{comment.customerId}</div>
          <div className={styles.buttons}>
            <button onClick={startUpdate} className={styles.updateButton} />
            <button onClick={handleDelete} className={styles.deleteButton} />
          </div>
        </div>
      </div>
      <div className={styles.bottom}>
        {!isUpdate ? (
          <div>댓글 내용 : {comment.comment_content}</div>
        ) : (
          <div>
            <input
              type="text"
              value={c.comment_content}
              onChange={(e) => setC({ ...c, comment_content: e.target.value })}
            ></input>
            <button onClick={handleUpdate}>수정완료</button>
          </div>
        )}
      </div>
    </div>
  );
}
export default CommentItem;
