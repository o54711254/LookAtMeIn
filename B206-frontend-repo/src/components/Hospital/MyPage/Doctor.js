import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axiosApi from "../../../api/axiosApi";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";

function Doctor() {
  const [doctorList, setDoctorList] = useState([]);
  const hospital = useSelector((state) => state.hospital);

  useEffect(() => {
    axiosApi
      .get(`/api/hospital/${hospital.hospitalSeq}/doctors`)
      .then((res) => {
        console.log(res.data);
        setDoctorList(res.data);
      })
      .catch((error) => {
        console.log("의사목록 불러오기 실패", error);
      });
  }, []);

  return (
    <div>
      <h2>의사 리스트</h2>
      <div>
        {doctorList.map((doctor) => (
          <div>
            <Link to={`/list/${doctor.id}`}>{doctor.doctor_name}</Link>
            <div>{doctor.docInfoName}</div>
          </div>
        ))}
      </div>
    </div>
  );
}
export default Doctor;
