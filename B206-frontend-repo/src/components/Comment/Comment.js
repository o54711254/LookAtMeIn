import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import CommentRegist from "./CommentRegist";
import CommentItem from "./CommentItem";

function Comment({ comments, freeboardSeq }) {
  return (
    <>
      <CommentRegist freeboardSeq={freeboardSeq} />
      <ul>
        {comments.map((comment, index) => (
          <CommentItem key={comment.comment_seq} comment={comment} />
        ))}
      </ul>
    </>
  );
}
export default Comment;
