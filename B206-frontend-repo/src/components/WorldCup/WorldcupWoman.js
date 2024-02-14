import React, { useState, useEffect } from "react";
import styles from "./WorldcupWoman.module.css";
import Aos from "aos";
import { useNavigate } from "react-router-dom";

const items = [
  {
    name: "고윤정",
    src: require("../../assets/woman/고윤정.jpg"),
  },
  {
    name: "민지",
    src: require("../../assets/woman/민지.jpg"),
  },
  {
    name: "지수",
    src: require("../../assets/woman/블랙핑크 지수.jpg"),
  },
  {
    name: "아이린",
    src: require("../../assets/woman/아이린.jpg"),
  },
  {
    name: "아이유",
    src: require("../../assets/woman/아이유.jpg"),
  },
  {
    name: "장원영",
    src: require("../../assets/woman/장원영.jpg"),
  },
  {
    name: "카리나",
    src: require("../../assets/woman/카리나.jpg"),
  },
  {
    name: "한소희",
    src: require("../../assets/woman/한소희.jpg"),
  },
];

const WorldcupWoman = () => {
  const navigate = useNavigate();
  const [cellebs, setCellebs] = useState([]);
  const [displays, setDisplays] = useState([]);
  const [winners, setWinners] = useState([]);

  useEffect(() => {
    items.sort(() => Math.random() - 0.5);
    setCellebs(items);
    setDisplays([items[0], items[1]]);
  }, []);

  const clickHandler = (celeb) => () => {
    if (cellebs.length <= 2 && winners.length === 0) {
      navigate("/worldcup/champion", { state: { winner: celeb } });
    } else {
      const newWinners = [...winners, celeb];
      const nextDisplays = cellebs.slice(2, 4);

      setCellebs((prev) => prev.slice(2));
      setDisplays(nextDisplays);

      if (nextDisplays.length < 2) {
        if (newWinners.length === 1) {
          navigate("/worldcup/champion", { state: { winner: newWinners[0] } });
        } else {
          setCellebs(newWinners);
          setDisplays([newWinners[0], newWinners[1]]);
          setWinners([]);
        }
      } else {
        setWinners(newWinners);
      }
    }
  };

  useEffect(() => {
    Aos.init({ duration: 800 });
  }, []);
  return (
    <div className={styles.worldcupContainer}>
      <div className={styles.head}>이상향 월드컵</div>
      <div className={styles.middle}>
        {displays.map((d) => (
          <div
            className={styles.contents}
            key={d.name}
            onClick={clickHandler(d)}
            data-aos="flip-left"
          >
            <img src={d.src} className={styles.img} />
            <div className={styles.name}>{d.name}</div>
          </div>
        ))}
      </div>
    </div>
  );
};
export default WorldcupWoman;
