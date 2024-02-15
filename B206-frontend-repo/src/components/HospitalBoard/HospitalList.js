import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import styles from "./HospitalList.module.css";
import profile from "../../assets/profile2.png";
import { useNavigate } from "react-router-dom";
import styled from "@emotion/styled";
import StarResult from "../ReviewBoard/StarRating/StarResult.js";
import { FaStar, FaStarHalf, FaRegStar } from "react-icons/fa";
import { css } from "@emotion/react";
import AOS from "aos";
import "aos/dist/aos.css";

const HospitalList = () => {
  const [hospitalInfo, setHospitalInfo] = useState([]);
  const [category, setCategory] = useState(null);
  const [profileImg, setProfileImg] = useState(profile);
  const navigate = useNavigate();

  useEffect(() => {
    axiosApi
      .get(`/api/hospital-info/list`) // API 엔드포인트를 적절한 URL로 변경해주세요.
      .then((response) => {
        console.log(response.data);
        const updateData = response.data.map((board) => {
          if (board.profileBase64 && board.profileType) {
            board.img = `data:${board.profileType};base64,${board.profileBase64}`;
          } else {
            board.img = profile;
          }
          return board;
        });
        setHospitalInfo(updateData);
      })
      .catch((error) => {
        console.error("병원 정보 리스트 조회 에러 : ", error);
      });
  }, []);

  const handleClick = (hospital_seq) => {
    navigate(`/hospital-info/detail/${hospital_seq}`);
  };

  useEffect(() => {
    AOS.init({
      duration: 200,
    });
  });

  return (
    <>
      <div className={styles.boardhead}>
        <div className={styles.headtitle}>
          <p>병원 소개</p>
        </div>
        <div className={styles.headtext}>
          <p>룩앳미인에서 다양한 병원 정보를 알아보세요</p>
        </div>
      </div>
      <div>
        {hospitalInfo.map((hospital) => (
          <li
            key={hospital.hospitalInfo_seq}
            className={styles.hospitalItem}
            data-aos="fade-up"
          >
            <div>
              {/* <img src={profile} alt="프로필" className={styles.profile} /> */}
              {hospital.img && (
                <img
                  src={hospital.img}
                  alt="자게 작성자 프사"
                  className={styles.profile}
                />
              )}
            </div>
            <div
              className={styles.hosInfo}
              onClick={() => handleClick(hospital.hospitalInfo_seq)}
            >
              <div className={styles.nameStar}>
                <div className={styles.hosName}>
                  {hospital.hospitalInfo_name} &nbsp;
                </div>
              </div>
              <div className={styles.hosAddress}>
                {hospital.hospitalInfo_address}
              </div>
              {/* <div className={styles.time}>
                <div>{hospital.hospitalInfo_open}</div>
                <div>{hospital.hospitalInfo_close}</div>
              </div> */}
            </div>
            <div className={styles.intro}>
              <div>{hospital.hospitalInfo_introduce}</div>
              <div className={styles.b}>
                {hospital.hospitalInfo_category.map((category, index) => (
                  <button key={index} className={styles.hashtagButton}>
                    {category.part}
                  </button>
                ))}
              </div>
            </div>
            <div className={styles.star}>
              <StarResult score={hospital.hospitalInfo_avgScore} />(
              {hospital.hospitalInfo_avgScore})
            </div>
          </li>
        ))}
      </div>
    </>
  );
};

export default HospitalList;
