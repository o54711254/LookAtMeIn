import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import StarResult from "./StarRating/StarResult";
import ReviewDelete from "./ReviewDelete";
import { Button } from "@mui/material";
import styles from "./ReviewDetail.module.css";
import profile from "../../assets/gun.png";

function ReviewDetail() {
  const [reviewDetail, setReviewDetail] = useState([]);
  const { reviewBoard_seq } = useParams(); // URL 파라미터에서 reviewBoard_seq를 가져옴
  const [imgURL, setImgURL] = useState('');
  const navigate = useNavigate();
  const [showButton, setShowButton] = useState(true);
  // 예제를 위한 현재 사용자 정보 (실제로는 로그인 데이터 등을 통해 설정해야 함)
  const currentUser = { name: "사용자 이름", role: "user" }; // 사용자 이름과 역할 설정

  const toggleButton = () => {
    setShowButton(!showButton);
  };

  useEffect(() => {
    axiosApi
      .get(`/api/reviewBoard/${reviewBoard_seq}`)
      .then((res) => {
        console.log(res.data);
        const base64 = res.data.base64;
        const type = res.data.type;

        const data = `data:${type};base64,${base64}`;
        if(base64 != null)
          setImgURL(data);

        setReviewDetail(res.data);
      })
      .catch((error) => {
        console.log("데이터를 불러오는데 실패했습니다.", error);
      });
  }, [reviewBoard_seq]);

  const onReviewUpdate = () => {
    navigate(`/reviewupdate`, { state: reviewDetail });
  };

  const onReviewDeleted = () => {
    navigate(`/reviewList`);
  };

  // 현재 사용자가 글쓴이와 같거나 관리자인지 확인
  const canEditOrDelete = currentUser.name === reviewDetail.customer_name || currentUser.role === "admin";

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <div className={styles.writer}>
          <img src={profile} alt="프로필" className={styles.profile}></img>
          <p>{reviewDetail.customer_name}</p>
        </div>
        <div className={styles.title}><p>{reviewDetail.reviewBoard_title}</p></div>
        <div className={styles.buttonbox}>
          {showButton && canEditOrDelete && (
            <>
              <Button onClick={onReviewUpdate}>수정</Button>
              <ReviewDelete reviewBoard_seq={reviewBoard_seq} onUpdated={onReviewDeleted}></ReviewDelete>
            </>
          )}
        </div>
      </div>
      <div className={styles.main}>
        <div className={styles.mainleft}>
          <div>시술 병원: {reviewDetail.reviewBoard_hospital}</div>
          <div>시술 부위: {reviewDetail.reviewBoard_surgery}</div>
          <div>담당 의사: {reviewDetail.reviewBoard_doctor}</div>
          <div>지역: {reviewDetail.reviewBoard_region}</div>
        </div>
        <div className={styles.maincenter}>
         <div className={styles.imgcon}>
          {imgURL ? <img src={imgURL} alt="글 사진"/> : <div>이미지 없음</div>}
          </div>
         <div>내용: {reviewDetail.reviewBoard_content}</div>
         <div className={styles.star}><StarResult score={reviewDetail.reviewBoard_score}/></div>
       </div>
       <div className={styles.mainright}>
          <p>의사 정보 들어갈 공간</p>
        </div>
      </div>
    </div>
  );
}

export default ReviewDetail;
