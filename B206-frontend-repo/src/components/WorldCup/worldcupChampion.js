import React, { useEffect } from "react";
import { useLocation } from "react-router-dom";
import styles from "./WorldcupChampion.module.css";
import Aos from "aos";

const WorldcupChampion = () => {
  const location = useLocation();
  const { winner } = location.state; // WorldcupMan에서 전달받은 우승자 정보

  useEffect(() => {
    Aos.init({
      duration: 800,
    });
  });

  return (
    <div className={styles.championContainer}>
      <div className={styles.top}>
        <div className={styles.head}>우승자는 {winner.name}입니다!</div>
        <img
          src={winner.src}
          alt={winner.name}
          className={styles.img}
          data-aos="zoom-in"
        />
      </div>
      <div className={styles.buttons}>
        <button className={styles.button}>결과 저장</button>
        <button className={styles.button}>얼굴 합성</button>
      </div>
    </div>
  );
};

export default WorldcupChampion;
