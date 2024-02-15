import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axiosApi from "../../../api/axiosApi";
import { useSelector } from "react-redux";
import styles from "./ReservationDetail.module.css";

function ReservationDetail() {
  const [reserveDetail, setReserveDetail] = useState({});
  const { reserveSeq } = useParams();
  const [questionnaire, setQuestionnaire] = useState({});
  const user = useSelector((state) => state.user);
  const navigate = useNavigate();
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
        setReserveDetail(res.data);
        setMeetingData((prevData) => ({
          ...prevData,
          userSeq: res.data.customerUserSeq,
          hospitalSeq: res.data.hospitalSeq,
          reserveSeq: res.data.reserveSeq,
        }));
        setQuestionnaire(res.data.questionnaireResponseDto);
      })
      .catch((error) => {
        console.log("안됨", error);
      });
  }, []);

  const handleMeetingClick = () => {
    navigate("/meeting", { state: { meetingData } });
  };

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
        <div className={styles.questionTitle}>문진표</div>
        <img src={questionnaire.base64} className={styles.questionImg} />
        <div>혈액형 : {questionnaire.blood}</div>
        <div>내용 : {questionnaire.remark}</div>
      </div>
      <button className={styles.suggestButton} onClick={handleMeetingClick}>
        상담하기
      </button>
    </div>
  );
}
export default ReservationDetail;
