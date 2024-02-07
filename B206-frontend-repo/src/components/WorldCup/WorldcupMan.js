import React, { useState, useEffect } from "react";

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
  const [cellebs, setCellebs] = useState([]);
  const [displays, setDisplays] = useState([]);
  const [winners, setWinners] = useState([]);
  useEffect(() => {
    items.sort(() => Math.random() - 0.5);
    setCellebs(items);
    setDisplays([items[0], items[1]]);
  }, []);

  const clickHandler = (food) => () => {
    if (cellebs.length <= 2) {
      if (winners.length === 0) {
        setDisplays([food]);
      } else {
        let updatedFood = [...winners, food];
        setCellebs(updatedFood);
        setDisplays([updatedFood[0], updatedFood[1]]);
        setWinners([]);
      }
    } else if (cellebs.length > 2) {
      setWinners([...winners, food]);
      setDisplays([cellebs[2], cellebs[3]]);
      setCellebs(cellebs.slice(2));
    }
  };
  return (
    <div>
      <h1 className="title">Favorite Worldcup</h1>
      {displays.map((d) => {
        return (
          <div className="flex-1" key={d.name} onClick={clickHandler(d)}>
            <img className="food-img" src={d.src} />
            <div className="name">{d.name}</div>
          </div>
        );
      })}
    </div>
  );
};

export default WorldcupMan;
