import React, { useEffect, useState } from "react";
import axiosApi from "../../api/axiosApi";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import FreeBoardDelete from "./FreeBoardDelete";
import FreeBoardUpdate from "./FreeBoardUpdate";
import Comment from "../Comment/Comment";
import styles from "./FreeBoardDetail.module.css";
import profile from "../../assets/profile2.png";
import { useSelector } from "react-redux";

function FreeBoardDetail() {
  const [post, setPost] = useState(null);
  const [img, setImg] = useState(null);
  const [profileImg, setProfileImg] = useState(null);
  const { freeboardSeq } = useParams();
  const navigate = useNavigate();
  const userRole = useSelector((state) => state.user.role);
  const userSeq = useSelector((state) => state.user.userSeq);

  const canEditOrDelete =
    userRole === "ADMIN" || userSeq === reviewDetail.user_seq;

  useEffect(() => {
    const fetchPost = async () => {
      try {
        let response = await axiosApi.get(
          `/api/freeBoard/freeBoardList/${freeboardSeq}`
        );
        setPost(response.data); // 먼저 게시글 정보를 설정

        setPost(response.data);
        console.log(response.data);

        const customerProfileBase64 = response.data.customerProfileBase64;
        const customerProfileType = response.data.customerProfileType;
        const profileData = `data:${customerProfileType};base64,${customerProfileBase64}`;
        if (customerProfileBase64) {
          setProfileImg(profileData);
        } else {
          setProfileImg(profile);
        }
        const base64 = response.data.base64;
        const type = response.data.type;
        const data = `data:${type};base64,${base64}`;
        if (base64 != null) setImg(data);
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
    <div className={styles.FreeBoardContainer}>
      <div className={styles.Head}>
        <div className={styles.writer}>
          <img src={profileImg} alt="프로필" className={styles.profileImg} />
          <div className={styles.idEmail}>
            <div className={styles.userId}>{post.userId}</div>
            <div className={styles.email}>{post.userEmail}</div>
          </div>
        </div>
        <div className={styles.buttons}>
          {canEditOrDelete && (
            <div className={styles.bb}>
              <FreeBoardUpdate
                freeboardContent={post.freeboardContent}
                freeboardTitle={post.freeboardTitle}
                freeboardSeq={post.freeboardSeq}
              />
              <FreeBoardDelete freeBoardSeq={freeboardSeq} />
            </div>
          )}
        </div>
      </div>
      <div className={styles.main}>
        <div className={styles.title}>{post.freeboardTitle}</div>
        <div className={styles.contents}>
          {/* <img src={img} alt="게시글 이미지" /> */}
          {img && (
            <img src={img} alt="게시글 이미지" className={styles.contentImg} />
          )}
          <div className={styles.contentText}>
            <div>{post.freeboardContent}</div>
          </div>
        </div>
      </div>
      {/* <div className={styles.horizon} /> */}
      {/* <div>해시태그: {post.hashTag}</div> */}

      <Comment comments={post.comments} freeboardSeq={post.freeboardSeq} />
    </div>
  );
}
export default FreeBoardDetail;
//뜬다!!
