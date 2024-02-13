import React, { useState, useEffect } from "react";
import { Routes, Route, NavLink } from "react-router-dom";
import axiosApi from "../../../../api/axiosApi";
import { useSelector } from "react-redux";
import styles from "./ProposeList.module.css";

function ProposeList() {
  const [proposeList, setProposeList] = useState([]);
  const userSeq = useSelector((state) => state.user.userSeq);
  const userName = useSelector((state) => state.user.userName);
  const [accept, setAccept] = useState({});
  // const [responseSeq, setResponseSeq] = useState(null);

  useEffect(() => {
    axiosApi
      .get(`api/mypage/notifications/${userSeq}`)
      .then((response) => {
        console.log(response.data);
        setProposeList(response.data);
        // setResponseSeq(response.data.seq);
      })
      .catch((error) => {
        console.error(
          "마이페이지에서 나에게 상담을 제안한 목록 조회 에러 : ",
          error
        );
      });
  }, [userSeq]);

  // 제안을 수락하는 함수. seq를 매개변수로 받음
  function startChat(seq) {
    axiosApi
      .post(`api/requestboard/accept-response/${seq}`)
      .then((response) => {
        console.log(response.data);
        //수락 요청 성공적으로 받으면, seq가 true로 바뀌면서 수락되었음 표시함.
        setAccept((prevSeqs) => ({ ...prevSeqs, [seq]: true }));
      })
      .catch((error) => {
        console.error("상담 수락 처리 중 에러 발생:", error);
      });
  }

  return (
    <div>
      <div className={styles.head}>
        <h3>{userName}님께 상담 요청을 보낸 병원 목록</h3>
      </div>
      <div>
        {proposeList.map((propose, index) => (
          <li key={index} className={styles.proposeItem}>
            <div className={styles.index}>No. {index + 1}</div>
            <div>{}</div>
            <div className={styles.writer}>
              <div>{propose.hospitalName}</div>
            </div>
            <div>
              <div>{propose.message}</div>
            </div>
            <div>
              {accept[propose.seq] ? (
                <button disabled>수락완료</button>
              ) : (
                <button onClick={() => startChat(propose.seq)}>수락하기</button>
              )}
            </div>
          </li>
        ))}
      </div>
    </div>
  );
}
export default ProposeList;
