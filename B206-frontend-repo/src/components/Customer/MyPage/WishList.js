import React, { useState, useEffect } from "react";
import Typography from "@mui/material/Typography";
import { useSelector } from "react-redux";
import axiosApi from "../../../api/axiosApi";
import styles from "./WishList.module.css"
import StarResult from "../../ReviewBoard/StarRating/StarResult";
import styled from "@emotion/styled";
import { FaStar, FaStarHalf, FaRegStar } from "react-icons/fa";
import { css } from "@emotion/react";
import profile from "../../../assets/gun.png";
import { useNavigate } from "react-router-dom";

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
        setHospitalInfo(response.data);
        setCategory(response.data.category);
      })
      .catch((error) => {
        console.error("서버로부터 응답을 받는 중 오류가 발생했습니다:", error);
      });
  }, [user_seq]);

  const handleClick = (hospital_seq) => {
    navigate(`/hospital-info/detail/${hospital_seq}`);
  };

  const StyledStar = styled.span`
    cursor: pointer;
    font-size: 1.5rem;
    color: orange;
    position: relative;

    ${({ isHalf }) =>
      isHalf &&
      css`
        width: 12px;
        overflow: hidden;

        &:nth-of-type(10) {
          transform: translate(-108px);
        }
        &:nth-of-type(8) {
          transform: translate(-84px);
        }
        &:nth-of-type(6) {
          transform: translate(-60px);
        }
        &:nth-of-type(4) {
          transform: translate(-36px);
        }
        &:nth-of-type(2) {
          transform: translate(-12px);
        }
      `}
  `;

  return (
      <>
      <div className={styles.container}>
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
            <li key={hospital.hospitalInfo_seq} className={styles.hospitalItem}>
              <div>
                <img src={profile} alt="프로필" className={styles.profile} />
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
                    {/* <StarResult score={hospital.reviewBoard_score} /> */}
                    <StyledStar isHalf={false}>
                      <FaStar />
                    </StyledStar>
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
      </>
    );
};

export default WishList;
