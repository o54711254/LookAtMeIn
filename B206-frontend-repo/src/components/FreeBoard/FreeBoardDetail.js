import React, { useEffect, useState } from "react";
import axiosApi from "../../api/axiosApi";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import FreeBoardDelete from "./FreeBoardDelete";
import FreeBoardUpdate from "./FreeBoardUpdate";
import Comment from "../Comment/Comment";
import downloadApi from "../../api/downloadApi";

function FreeBoardDetail() {
  const [post, setPost] = useState(null);
  const [img, setImg] = useState(null);
  const { freeboardSeq } = useParams();
  const navigate = useNavigate();

  // useEffect(() => {
  //   const fetchPost = async () => {
  //     try {
  //       let response = await axiosApi.get(
  //         `/api/freeBoard/freeBoardList/${freeboardSeq}`
  //       );

  //       setPost(response.data);// 먼저 게시글 정보를 설정

  //       const response2 = await axiosApi.get(
  //         `/api/file/${response.data.freeboardSeq}`
  //       )
  //         console.log(response2.data);
  //       setImg(response2.data);
  //       // setPost(response.data);
  //       console.log(response.data);
  //     } catch (error) {
  //       console.log("자유게시판 상세 불러오기 실패 : ", error);
  //     }
  //   };
  //   if (freeboardSeq) {
  //     fetchPost();
  //   }
  // }, [freeboardSeq]);
  useEffect(() => {
    const fetchPost = async () => {
      try {
        let response = await axiosApi.get(
          `/api/freeBoard/freeBoardList/${freeboardSeq}`
        );
        setPost(response.data); // 먼저 게시글 정보를 설정

        setPost(response.data);
        console.log(response.data);
        const imgResponse = await axiosApi.get(
          response.data.fileUrl
        )
        console.log("response2: ", imgResponse);
        const base64 = imgResponse.data.base64;
        const type = imgResponse.data.type;

        const data =`data:${type};base64,${base64}`;
        setImg(data);

      } catch (error) {
        console.log("자유게시판 상세 불러오기 실패: ", error);
      }
    };

    if (freeboardSeq) {
      fetchPost();
    }
  }, [freeboardSeq]);
  //에러찾을라고..
  if (!post) {
    return <div>Loading...</div>;
  }

  return (
    <>
      <h3>자유 게시판 상세</h3>
      <div>작성자 아이디: {post.userId}</div>
      <div>작성자 이메일: {post.userEmail}</div>
      <div>작성 날짜: {post.freeboardRegisterdate}</div>
      {/* <img src={img} alt="게시글 이미지" /> */}
      {img ? <img src={img} alt="게시글 이미지" /> : <div>이미지 없음</div>}
      <div>글 내용: {post.freeboardContent}</div>
      <div>글 제목: {post.freeboardTitle}</div>
      {/* <div>해시태그: {post.hashTag}</div> */}
      <FreeBoardDelete freeBoardSeq={freeboardSeq} />
      <FreeBoardUpdate
        freeboardContent={post.freeboardContent}
        freeboardTitle={post.freeboardTitle}
        freeboardSeq={post.freeboardSeq}
      />

      <Comment comments={post.comments} freeboardSeq={post.freeboardSeq} />
    </>
  );
}
export default FreeBoardDetail;
//뜬다!!
