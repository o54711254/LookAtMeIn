import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import StarResult from "./StarRating/StarResult";
import ReviewDelete from "./ReviewDelete";
import { Button } from "@mui/material";
import styles from "./ReviewDetail.module.css";
import profile from "../../assets/profile2.png";
import Report from "../Modal/ReviewReport";
import { useSelector } from "react-redux";

function ReviewDetail() {
  const [reviewDetail, setReviewDetail] = useState([]);
  const { reviewBoard_seq } = useParams(); // URL 파라미터에서 reviewBoard_seq를 가져옴
  const navigate = useNavigate();
  const [showButton, setShowButton] = useState(true);
  // 예제를 위한 현재 사용자 정보 (실제로는 로그인 데이터 등을 통해 설정해야 함)
  // const currentUser = { name: "사용자 이름", role: "user" }; // 사용자 이름과 역할 설정
  const userRole = useSelector((state) => state.user.role);
  const userSeq = useSelector((state) => state.user.userSeq);

  const toggleButton = () => {
    setShowButton(!showButton);
  };

  const [profileImg, setProfileImg] = useState(null);
  const [img, setImg] = useState(null);

  useEffect(() => {
    axiosApi
      .get(`/api/reviewBoard/${reviewBoard_seq}`)
      .then((response) => {
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

        setReviewDetail(response.data);
      })
      .catch((error) => {
        console.log("데이터를 불러오는데 실패했습니다.", error);
      });
  }, [reviewBoard_seq]);

  const onReviewUpdate = () => {
    navigate(`/reviewupdate`, { state: reviewDetail });
  };

  const onReviewDeleted = () => {
    navigate(`/reviewBoard/list`);
  };

  // 현재 사용자가 글쓴이와 같거나 관리자인지 확인
  const canEditOrDelete =
    userRole === "ADMIN" || userSeq === reviewDetail.user_seq;

  return (
    <div className={styles.container}>
      <div className={styles.Head}>
        <div className={styles.writer}>
          <img
            src={profileImg}
            alt="프로필"
            className={styles.profileImg}
          ></img>
          <div className={styles.name}>{reviewDetail.customer_name}</div>
        </div>
        <div className={styles.title}>{reviewDetail.reviewBoard_title}</div>
      </div>
      <div className={styles.main}>
        <div className={styles.left}>
          <div className={styles.leftContainer}>
            <div className={styles.bottom}>
              <div className={styles.a}>
                <div className={styles.tt}>시술 병원</div>
                <div className={styles.cc}>
                  {reviewDetail.reviewBoard_hospital}
                </div>
              </div>
              <div className={styles.a}>
                <div className={styles.tt}>시술 부위 </div>
                <div className={styles.cc}>
                  {reviewDetail.reviewBoard_surgery}
                </div>
              </div>
              <div className={styles.a}>
                <div className={styles.tt}>담당 의사 </div>
                <div className={styles.cc}>
                  {reviewDetail.reviewBoard_doctor}
                </div>
              </div>
              <div className={styles.a}>
                <div className={styles.tt}>지역</div>
                <div className={styles.cc}>
                  {reviewDetail.reviewBoard_region}
                </div>
              </div>
            </div>
            <div className={styles.bottom}>
              <div className={styles.p}>
                상담 견적가 : <em>{reviewDetail.reviewBoard_expected_price}</em>
                원
              </div>
              <div className={styles.p}>
                실제 시술가 : <em>{reviewDetail.reviewBoard_surgery_price}</em>
                원
              </div>
            </div>
          </div>
        </div>
        <div className={styles.right}>
          <div className={styles.imgcon}>
            {img ? (
              <img src={img} alt="글 사진" className={styles.reviewImage} />
            ) : (
              <div>이미지 없음</div>
            )}
          </div>

          <div className={styles.content}>
            {reviewDetail.reviewBoard_content}
          </div>
          <div className={styles.star}>
            <StarResult score={reviewDetail.reviewBoard_score} />
          </div>
        </div>
      </div>
      <div className={styles.buttonbox}>
        <div>
          <Report reviewBoard_seq={reviewBoard_seq} />
        </div>
        <div>
          {showButton && canEditOrDelete && (
            <div className={styles.bb}>
              <button
                className={styles.update}
                onClick={onReviewUpdate}
              ></button>
              <ReviewDelete
                reviewBoard_seq={reviewBoard_seq}
                onUpdated={onReviewDeleted}
              ></ReviewDelete>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default ReviewDetail;
