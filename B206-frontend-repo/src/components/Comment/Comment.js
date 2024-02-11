import React from "react";
import CommentRegist from "./CommentRegist";
import CommentItem from "./CommentItem";
import styles from "./Comment.module.css";

function Comment({ comments, freeboardSeq }) {
  return (
    <div className={styles.commentContiner}>
      <CommentRegist freeboardSeq={freeboardSeq} />
      <div>
        {comments.map((comment, index) => (
          <CommentItem key={comment.comment_seq} comment={comment} />
        ))}
      </div>
    </div>
  );
}
export default Comment;
