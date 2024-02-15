import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axiosApi from "../../../api/axiosApi";
import { useSelector } from "react-redux";
import DoctorRegist from "./DoctorResgist";
import profile from "../../../assets/profile2.png";
import StarResult from "../../ReviewBoard/StarRating/StarResult";
import styles from "./Doctor.module.css";

//test  2
function Doctor() {
  const [doctorList, setDoctorList] = useState([]);
  const hospital = useSelector((state) => state.hospital);
  const [profileImg, setProfileImg] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    axiosApi
      .get(`/api/hospital/${hospital.hospitalSeq}/doctors`)
      .then((res) => {
        console.log(res.data);
        setDoctorList(res.data);
        const base64 = res.data.doctorProfileBase64;
        const type = res.data.doctorProfileType;
        if (base64) {
          const data = `data:${type};base64,${base64}`;
          setProfileImg(data);
        } else {
          setProfileImg(profile);
        }
      })
      .catch((error) => {
        console.log("의사목록 불러오기 실패", error);
      });
  }, []);

  const goReview = (doctorId) => {
    navigate(`/search/${doctorId}`);
  };

  return (
    <div className={styles.doctorContainer}>
      <div className={styles.itemContainer}>
        {doctorList.map((doctor, index) => (
          <div
            className={styles.doctorItem}
            onClick={() => goReview(doctor.doctorName)}
          >
            {/* <Link to={`/list/${doctor.id}`}>{doctor.doctorName}</Link> */}
            <div className={styles.head}>
              <img src={profileImg} className={styles.profile} />
              <div>{doctor.doctorName} 원장</div>
            </div>
            <div className={styles.contents}>
              <div>전문 분야 : {doctor.doctorCategory}</div>
              <div className={styles.score}>
                평점 : <StarResult score={doctor.doctorAvgScore} />(
                {doctor.doctorAvgScore})
              </div>
              <div>후기 수 : {doctor.doctorCntReviews}</div>
            </div>
          </div>
        ))}
      </div>
      <DoctorRegist />
    </div>
  );
}
export default Doctor;
