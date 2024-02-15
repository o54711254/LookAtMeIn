import React, { useState, useEffect } from "react";
import styles from "./WorldcupMan.module.css";
import Aos from "aos";
import { useNavigate } from "react-router-dom";

const items = [
  {
    name: "남주혁",
    src: require("../../assets/man/남주혁.jpg"),
  },
  {
    name: "박보검",
    src: require("../../assets/man/박보검.jpg"),
  },
  {
    name: "뷔",
    src: require("../../assets/man/뷔.jpg"),
  },
  {
    name: "서강준",
    src: require("../../assets/man/서강준.jpg"),
  },
  {
    name: "유승호",
    src: require("../../assets/man/유승호.jpg"),
  },
  {
    name: "이민호",
    src: require("../../assets/man/이민호.jpg"),
  },
  {
    name: "정해인",
    src: require("../../assets/man/정해인.jpg"),
  },
  {
    name: "차은우",
    src: require("../../assets/man/차은우.jpg"),
  },
];

const WorldcupMan = () => {
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

export default WorldcupMan;
