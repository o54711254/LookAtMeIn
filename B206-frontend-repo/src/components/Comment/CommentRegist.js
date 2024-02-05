import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axios from "axios";
function CommentRegist({ freeboardSeq }) {
  const [isRegist, setIsRegist] = useState(false);

  const [comment, setComment] = useState({
    comment_content: "",
    freeboard_seq: freeboardSeq,
    user_id: "",
    user_email: "",
    regdate: "",
  });
  return <></>;
}
export default CommentRegist;
