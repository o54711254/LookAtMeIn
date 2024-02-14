import styled from "@emotion/styled";
import { FaStar, FaStarHalf, FaRegStar } from "react-icons/fa";
import { css } from "@emotion/react";

const StyledStar = styled.span`
  cursor: pointer;
  font-size: 1rem;
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

const StarRatingDisplay = ({ score }) => {
  let stars = [];
  for (let i = 1; i <= 5; i++) {
    if (i <= score) {
      stars.push(
        <StyledStar key={i} isHalf={false}>
          <FaStar />
        </StyledStar>
      );
    } else if (i === Math.ceil(score) && !Number.isInteger(score)) {
      stars.push(
        <StyledStar key={i} isHalf={true}>
          <FaStarHalf />
        </StyledStar>
      );
    } else {
      stars.push(
        <StyledStar key={i} isHalf={false}>
          <FaRegStar />
        </StyledStar>
      );
    }
  }

  return <div>{stars}</div>;
};

export default StarRatingDisplay;
