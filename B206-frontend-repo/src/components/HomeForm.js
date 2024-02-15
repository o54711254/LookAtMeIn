import React from "react";
import { Link, useNavigate } from "react-router-dom";
import styles from "./HomeForm.module.css";
import eye from "../assets/home/eye.png";
import chest from "../assets/home/chest.png";
import fat from "../assets/home/fat.png";
import hairLoss from "../assets/home/hairLoss.png";
import jaw from "../assets/home/jaw.png";
import lip from "../assets/home/lip.png";
import nose from "../assets/home/nose.png";
import syringe from "../assets/home/syringe.png";
import tooth from "../assets/home/tooth.png";
import wrinkle from "../assets/home/wrinkle.png";

function Home() {
  const navigate = useNavigate();

  const handleSearch = (term) => {
    navigate(`/search/${term}`);
  };
  return (
    <>
      <div className={styles.container}>
        <div className={styles.icons}>
          <img src={eye} alt="눈 아이콘" onClick={() => handleSearch("눈")} />
          <img src={nose} alt="코 아이콘" onClick={() => handleSearch("코")} />
          <img src={lip} alt="입 아이콘" onClick={() => handleSearch("입술")} />
          <img
            src={wrinkle}
            alt="주름 아이콘"
            onClick={() => handleSearch("주름")}
          />
          <img
            src={jaw}
            alt="윤곽 아이콘"
            onClick={() => handleSearch("안면윤곽")}
          />
        </div>
        <div className={styles.icons}>
          <img
            src={fat}
            alt="지방흡입 아이콘"
            onClick={() => handleSearch("지방 흡입")}
          />
          <img
            src={chest}
            alt="가슴 아이콘"
            onClick={() => handleSearch("가슴")}
          />
          <img
            src={hairLoss}
            alt="탈모 아이콘"
            onClick={() => handleSearch("탈모")}
          />
          <img
            src={syringe}
            alt="주사기 아이콘"
            onClick={() => handleSearch("시술")}
          />
          <img
            src={tooth}
            alt="치아 아이콘"
            onClick={() => handleSearch("치아 미백")}
          />
        </div>
      </div>
    </>
  );
}

export default Home;
