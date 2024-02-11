import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import { useParams, useNavigate } from "react-router-dom";
// import Box from "@mui/material/Box";
// import div from "@mui/material/div";
import Reserve from "../Modal/DateTimePickerModal";
import { useDispatch } from "react-redux";
import { setHospital } from "../../redux/hospital.js";
import styles from "./HospitalInfo.module.css";
import basicHos from "../../assets/basicHos.png";
import profile from "../../assets/gun.png";
import StarResult from "../ReviewBoard/StarRating/StarResult.js";

const HospitalInfo = () => {
  const dispatch = useDispatch();
  const { hospital_seq } = useParams();
  const [hospitalData, setHospitalData] = useState({
    hospitalInfo_seq: "",
    hospitalInfo_name: "",
    hospitalInfo_phoneNumber: "",
    hospitalInfo_introduce: "",
    hospitalInfo_address: "",
    hospitalInfo_open: "",
    hospitalInfo_close: "",
    hospitalInfo_url: "",
    userSeq: "",
    avgScore: "",
    cntReviews: "",
  });

  const [img, setImg] = useState(null);
  useEffect(() => {
    const getHospitalInfo = async () => {
      try {
        const response = await axiosApi.get(
          `/api/hospital-info/detail/${hospital_seq}`
        );
        setHospitalData(response.data);
        console.log("여기", response.data);
        // const imgResponse = await axiosApi.get(response.data.fileUrl);
        // console.log("response2: ", imgResponse);
        // const base64 = imgResponse.data.base64;
        // const type = imgResponse.data.type;
        // const data = `data:${type};base64,${base64}`;
        // setImg(data);

        dispatch(
          setHospital({
            hospitalSeq: response.data.userSeq,
            hospitalName: response.data.hospitalInfo_name,
          })
        );
      } catch (error) {
        console.error("병원 정보를 가져오는데 실패했습니다:", error);
      }
    };

    getHospitalInfo();
  }, []);

  const navigate = useNavigate();
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    axiosApi
      .get(`/api/hospital-info/reviews/${hospital_seq}`)
      .then((response) => {
        console.log(response.data);
        setReviews(response.data);
      })
      .catch((error) => {
        console.log("병원별 후기 목록 불러오기 실패", error);
      });
  }, []);

  const handleClick = (reviewBoard_seq) => {
    navigate(`api/reviewBoard/{seq}`);
  };

  const [doctors, setDoctors] = useState([]);
  useEffect(() => {
    axiosApi
      .get(`api/hospital-info/doctors/${hospital_seq}`)
      .then((response) => {
        console.log(response.data);
        setDoctors(response.data);
      })
      .catch((error) => {
        console.log("의사 목록 불러오기 실패 : ", error);
      });
  }, []);

  const week = ["월", "화", "수", "목", "금", "토", "일"];
  return (
    <>
      <div className={styles.part}>
        <div className={styles.container}>
          <div className={styles.imgTitle}>
            <div className={styles.profile}>
              {img ? (
                <img src={img} alt="게시글 이미지" />
              ) : (
                <img src={basicHos} alt="병원 기본 프사" />
              )}
            </div>

            <div className={styles.title}>{hospitalData.hospitalInfo_name}</div>
          </div>
          <div className={styles.address}>
            <div>주소</div>
            {hospitalData.hospitalInfo_address}
          </div>
          <div className={styles.url}>
            <div>홈페이지 </div>
            {hospitalData.hospitalInfo_url}
          </div>
          <div className={styles.time}>
            <div>진료시간</div>
            {week.map((day) => (
              <div>
                {day === "토" && (
                  <div>
                    {day} {hospitalData.hospitalInfo_open} ~ 13:00{" "}
                  </div>
                )}
                {day === "일" && <div>{day} 휴무</div>}
                {!(day === "토" || day === "일") && (
                  <div>
                    {day} {hospitalData.hospitalInfo_open} ~{" "}
                    {hospitalData.hospitalInfo_close}{" "}
                  </div>
                )}
              </div>
            ))}
          </div>
          <div className={styles.Number}>
            <div>전화번호</div> {hospitalData.hospitalInfo_phoneNumber}
          </div>
          <div className={styles.info}>
            <div>소개</div>
            {hospitalData.hospitalInfo_introduce}
          </div>

          {/* <div>avgScore: {hospitalData.avgScore}</div> */}
        </div>
        <Reserve />
      </div>
      <div className={styles.part}>
        {reviews.map((review, key) => (
          <li
            key={review.reviewBoard_seq}
            className={styles.reviewItem}
            onClick={() => handleClick(review.reviewBoard_seq)}
          >
            <div>
              <img src={profile} alt="프로필" className={styles.profile} />
            </div>
            <div className={styles.writer}>
              <div>{review.customer_name}</div>
              <div>
                <div>
                  <StarResult score={review.reviewBoard_score} />
                </div>
              </div>
            </div>
            <div className={styles.title}>
              <div>{review.reviewBoard_title}</div>
            </div>
            <div className={styles.price}>
              시술가 : {review.reviewBoard_price} 원
            </div>
          </li>
        ))}
      </div>
      <div className={styles.part}></div>
    </>
  );
};

export default HospitalInfo;
