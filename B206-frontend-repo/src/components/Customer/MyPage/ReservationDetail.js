import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosApi from "../../../api/axiosApi";

function ReservationDetail() {
  const [reserveDetail, setReserveDetail] = useState({});
  const { reserveSeq } = useParams();
  const [questionnaire, setQuestionnaire] = useState({});

  useEffect(() => {
    axiosApi
      .get(`/api/reserve/detail/${reserveSeq}`)
      .then((res) => {
        console.log("예약상세", res.data);
        setReserveDetail(res.data);
        setQuestionnaire(res.data.questionnaireResponseDto);
        console.log("문진표내용", questionnaire);
      })
      .catch((error) => {
        console.log("안됨", error);
      });
  }, []);
  return (
    <div>
      <div>{reserveDetail.customerName}</div>
      <div>{reserveDetail.hopitalName}</div>
      <div>{reserveDetail.year}</div>
      <div>{reserveDetail.month}</div>
      <div>{reserveDetail.day}</div>
      <div>{reserveDetail.dayofweek}</div>
      <div>{reserveDetail.time - 12}</div>
      <div>문진표</div>
      <div>{questionnaire.blood}</div>
      <div>{questionnaire.remark}</div>
      <img src={questionnaire.base64} />
    </div>
  );
}
export default ReservationDetail;
