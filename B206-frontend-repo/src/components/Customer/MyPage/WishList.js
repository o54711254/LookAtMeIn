import React, { useState, useEffect } from "react";
import Typography from "@mui/material/Typography";
import { useSelector } from "react-redux";
import axiosApi from "../../../api/axiosApi";
import styles from "./WishList.module.css";
import StarResult from "../../ReviewBoard/StarRating/StarResult";
import styled from "@emotion/styled";
import { FaStar, FaStarHalf, FaRegStar } from "react-icons/fa";
import { css } from "@emotion/react";
import profile from "../../../assets/profile2.png";
import { useNavigate } from "react-router-dom";
import AOS from "aos";
import "aos/dist/aos.css";

const WishList = () => {
  const [hospitalInfo, setHospitalInfo] = useState([]);
  const [category, setCategory] = useState([]);
  const navigate = useNavigate();

  const user_seq = useSelector((state) => state.user.userSeq);

  useEffect(() => {
    axiosApi
      .get(`/api/favorites/list/${user_seq}`)
      .then((response) => {
        console.log(response.data);
        const updateData = response.data.map((board) => {
          if (board.customerProfileBase64 && board.customerProfileType) {
            board.img = `data:${board.customerProfileType};base64,${board.customerProfileBase64}`;
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
  }, [user_seq]);

  const handleClick = (hospital_seq) => {
    navigate(`/hospital-info/detail/${hospital_seq}`);
  };

  return (
    <div className={styles.container}>
      <div className={styles.boardhead}>
        <div className={styles.headtitle}>찜한 병원 목록</div>
        <div className={styles.headtext}>관심가는 병원을 관리하세요</div>
      </div>
      <div className={styles.itemContainer}>
        {hospitalInfo.map((hospital) => (
          <li key={hospital.hospitalInfo_seq} className={styles.hospitalItem}>
            <div>
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
                <div>
                  <StarResult score={hospital.hospitalInfo_avgScore} />
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
              <div className={styles.hashtagButton}>해시태그</div>
            </div>
          </li>
        ))}
      </div>
    </div>
  );
};

export default WishList;
