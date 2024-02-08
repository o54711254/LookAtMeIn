import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import StarResult from "./StarRating/StarResult";
import ReviewDelete from "./ReviewDelete";
import { Button } from "@mui/material";
import styles from "./ReviewDetail.module.css"
import profile from "../../assets/gun.png"

// axios 완료 (reviewBoard_seq 넘어오는 것만 확인하면 될 듯)

function ReviewDetail() {
  const [reviewDetail, setReviewDetail] = useState([]);
  const { reviewBoard_seq } = useParams(); // URL 파라미터에서 reviewBoard_seq를 가져옴
  const [imgURL, setImgURL] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    axiosApi
      .get(`/api/reviewBoard/${reviewBoard_seq}`)
      .then((res) => {
        console.log(res.data);
        const base64 = res.data.base64;
        const type = res.data.type;

        const data = `data:${type};base64,${base64}`;
        setImgURL(data);

        setReviewDetail(res.data);
      })
      .catch((error) => {
        console.log("데이터를 불러오는데 실패했습니다.", error);
      });
  }, []);

  const onReviewUpdate = () => {
    navigate(`/reviewupdate`, { state: reviewDetail });
  };

  const onReviewDeleted = () => {
    navigate(`/reviewList`);
  };

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <div className={styles.writer}><img src={profile} alt="aa" className={styles.profile}></img><p>{reviewDetail.customer_name}</p></div>
        <div className={styles.title}><p>{reviewDetail.reviewBoard_title}</p></div>
      </div>
      <div className={styles.main}>
        <div className={styles.mainleft}>
          <div>시술 병원: {reviewDetail.reviewBoard_hospital}</div>
          <div>시술 부위: {reviewDetail.reviewBoard_surgery}</div>
          <div>담당 의사: {reviewDetail.reviewBoard_doctor}</div>
          <div>지역: {reviewDetail.reviewBoard_region}</div>
       </div>
        <div className={styles.maincenter}>
         <div className={styles.imgcon}><img src={imgURL} alt="글 사진"/></div>
         <div>내용: {reviewDetail.reviewBoard_content}</div>
         <div className={styles.star}><StarResult score={reviewDetail.reviewBoard_score}/></div>
       </div>
       <div className={styles.mainright}>
          <p>의사 정보 들어갈 공간</p>
        </div>
      </div>
      {/* <div>
        <Button onClick={onReviewUpdate}>수정</Button>
        <ReviewDelete
          reviewBoard_seq={reviewBoard_seq}
          onUpdated={onReviewDeleted}
          ></ReviewDelete>
          </div> */}
    </div>
  );
}

export default ReviewDetail;
