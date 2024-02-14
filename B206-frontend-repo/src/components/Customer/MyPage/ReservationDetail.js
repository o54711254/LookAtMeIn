import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosApi from "../../../api/axiosApi";
import { useSelector } from "react-redux";
import styles from "./ReservationDetail.module.css";

function ReservationDetail() {
  const [reserveDetail, setReserveDetail] = useState({});
  const { reserveSeq } = useParams();
  const [questionnaire, setQuestionnaire] = useState({});
  const user = useSelector((state) => state.user);
  const [meetingData, setMeetingData] = useState({
    userSeq: "",
    hospitalSeq: "",
    role: user.role,
    reserveSeq: "",
  });
  useEffect(() => {
    axiosApi
      .get(`/api/reserve/detail/${reserveSeq}`)
      .then((res) => {
        console.log(res.data);
        setReserveDetail(res.data);
        setMeetingData((prevData) => ({
          ...prevData,
          userSeq: res.data.customerUserSeq,
          hospitalSeq: res.data.hospitalUserSeq,
          reserveSeq: res.data.reserveSeq,
        }));
        console.log("미팅데이터", meetingData);
        setQuestionnaire(res.data.questionnaireResponseDto);
      })
      .catch((error) => {
        console.log("안됨", error);
      });
  }, []);

  return (
    <div className={styles.detailContainer}>
      <div className={styles.top}>
        <div className={styles.title}>{reserveDetail.hospitalName}</div>
        <div className={styles.date}>
          <div>예약시간 : </div>
          <div>{reserveDetail.year}년</div>
          <div>{reserveDetail.month}월</div>
          <div>{reserveDetail.day}일</div>
          <div>{reserveDetail.dayofweek}</div>
          <div>{reserveDetail.time - 12}시</div>
        </div>
      </div>
      <div className={styles.questionnaire}>
        <img src={questionnaire.base64} className={styles.questionImg} />
        <div>{questionnaire.blood}</div>
        <div>{questionnaire.remark}</div>
      </div>
    </div>
  );
}
export default ReservationDetail;
