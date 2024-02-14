import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosApi from "../../../api/axiosApi";

function ReservationDetail() {
  const [reserveDetail, setReserveDetail] = useState(null);
  const { reserveSeq } = useParams();

  useEffect(() => {
    axiosApi
      .get(`/api/reserve/detail/${reserveSeq}`)
      .then((res) => {
        console.log(res.data);
        setReserveDetail(res.data);
      })
      .catch((error) => {
        console.log("안됨", error);
      });
  }, []);
  return <div>d</div>;
}
export default ReservationDetail;
