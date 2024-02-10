import React from "react";
import Typography from "@mui/material/Typography";
import styles from "./HospitalBoardSearch.module.css";
import profile from "../../../assets/gun.png";
import { useNavigate } from "react-router-dom";
import styled from "@emotion/styled";
import { FaStar, FaStarHalf, FaRegStar } from "react-icons/fa";
import { css } from "@emotion/react";
import SearchInput from "../SearchInput";

// results를 props로 추가합니다.
const HospitalList = ({ results }) => {
  const navigate = useNavigate();

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
        &:nth-of-type(10) { transform: translate(-108px); }
        &:nth-of-type(8) { transform: translate(-84px); }
        &:nth-of-type(6) { transform: translate(-60px); }
        &:nth-of-type(4) { transform: translate(-36px); }
        &:nth-of-type(2) { transform: translate(-12px); }
      `}
  `;

  return (
    <>
      <div>
        {results.map((hospital) => (
          <li
            key={hospital.hospitalInfo_seq}
            onClick={() => handleClick(hospital.hospitalInfo_seq)}
            className={styles.reviewItem}
          >
            <div>
              <img src={profile} alt="프로필" className={styles.profile} />
            </div>
            <div className={styles.writer}>
              <Typography>{hospital.user.name}</Typography>
              <div className={styles.time}>
                <Typography>{hospital.hospitalInfo_open} - {hospital.hospitalInfo_close}</Typography>
              </div>
              <StyledStar isHalf={false}>
                <FaRegStar />
              </StyledStar>
            </div>
            <div className={styles.title}>
              <Typography>{hospital.hospitalInfo_introduce}</Typography>
            </div>
          </li>
        ))}
      </div>
    </>
  );
};

export default HospitalList;
