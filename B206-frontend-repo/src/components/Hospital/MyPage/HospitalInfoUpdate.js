import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { loginUser } from "../../../redux/user";
import axiosApi from "../../../api/axiosApi";
import styles from "./HospitalInfoUpdate.module.css";
import profile from "../../../assets/man/유승호.jpg"; // 데모 이미지로 가정
import check from "../../../assets/check.png"; // 데모 체크 이미지로 가정
import { loginHospital } from "../../../redux/hospital";

function base64ToBlob(base64, mimeType) {
  const byteCharacters = atob(base64);
  const byteNumbers = new Array(byteCharacters.length);
  for (let i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i);
  }
  const byteArray = new Uint8Array(byteNumbers);
  return new Blob([byteArray], { type: mimeType });
}

function InfoUpdate() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const hospital = useSelector((state) => state.hospital);
  const [initialData, setInitialData] = useState({});
  const [updateData, setUpdateData] = useState({});

  useEffect(() => {
    axiosApi
      .get(`/api/mypage/${hospital.userSeq}`)
      .then((res) => {
        setInitialData(res.data);
        setUpdateData(res.data);
      })
      .catch((error) => {
        console.error("데이터를 불러오는 중 에러 발생", error);
      });
  }, [hospital.userSeq]); // userSeq가 변경될 때마다 데이터를 다시 불러옵니다.

  const handleInputChange = (e) => {
    setUpdateData({
      ...updateData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const base64Data = hospital.profileImg.split(",")[1];
    const imageBlob = base64ToBlob(base64Data, "image/png");

    const formData = new FormData();
    formData.append("profile", imageBlob, "profile.png");
    formData.append(
      "hospitalData",
      JSON.stringify({
        hospitalInfo_id: updateData.hospitalInfo_id,
        hospitalInfo_password: updateData.hospitalInfo_password,
        hospitalInfo_name: updateData.hospitalInfo_name,
        hospitalInfo_phoneNumber: updateData.hospitalInfo_phoneNumber,
        hospitalInfo_email: updateData.hospitalInfo_email,
        hospitalInfo_introudce: updateData.hospitalInfo_introudce,
        hospitalInfo_address: updateData.hospitalInfo_address,
        hospitalInfo_open: updateData.hospitalInfo_open,
        hospitalInfo_close: updateData.hospitalInfo_close,
        hospitalInfo_url: updateData.hospitalInfo_url,
      })
    );

    axiosApi
      .put(`/api/hospital/mypage/modify/${hospital.userSeq}`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((res) => {
        console.log("수정성공", res);
        dispatch(
          loginHospital({
            ...hospital,
            ...updateData,
            profileImg: hospital.profileImg, // 기존 이미지 정보를 유지
          })
        );
        navigate(-1); // 이전 페이지로 이동
      })
      .catch((error) => {
        console.error("업로드 중 에러 발생", error);
      });
  };
  return (
    <div className={styles.infoContainer}>
      <div className={styles.infoTop}>
        <img
          src={hospital.profileImg}
          alt="profileImg"
          className={styles.profileImg}
        />
        <div className={styles.infoName}>
          {updateData.hospitalInfo_name}님 반갑습니다!
          <div className={styles.infoId}>ID : {updateData.hospitalInfo_id}</div>
        </div>
      </div>
      <form className={styles.updateContainer} onSubmit={handleSubmit}>
        <div className={styles.box}>
          <div className={styles.infoMiddle}>
            <div className={styles.info}>고객정보</div>
            <img
              src={check}
              alt="updateIcon"
              className={styles.icon}
              onClick={handleSubmit}
            />
          </div>
          <div className={styles.infoBottom}>
            <div className={styles.title}>병원명</div>
            <input
              className={styles.contents}
              name="hospitalInfo_name"
              value={updateData.hospitalInfo_name || ""}
              onChange={handleInputChange}
            />
            <div className={styles.title}>주소</div>
            <input
              className={styles.contents}
              name="hospitalInfo_address"
              value={updateData.hospitalInfo_address || ""}
              onChange={handleInputChange}
            />
            <div className={styles.title}>이메일</div>
            <input
              className={styles.contents}
              name="hospitalInfo_email"
              type="email"
              value={updateData.hospitalInfo_email || ""}
              onChange={handleInputChange}
            />
            <div className={styles.title}>전화번호</div>
            <input
              className={styles.contents}
              name="hospitalInfo_phoneNumber"
              value={updateData.hospitalInfo_phoneNumber || ""}
              onChange={handleInputChange}
            />
            <div className={styles.title}>오픈시간</div>
            <input
              className={styles.contents}
              name="HospitalInfo_open"
              value={updateData.hospitalInfo_open || ""}
              onChange={handleInputChange}
            />
            <div className={styles.title}>마감시간</div>
            <input
              className={styles.contents}
              name="HospitalInfo_close"
              value={updateData.hospitalInfo_close || ""}
              onChange={handleInputChange}
            />
            <div className={styles.title}>홈페이지</div>
            <input
              className={styles.contents}
              name="HospitalInfo_url"
              value={updateData.hospitalInfo_url || ""}
              onChange={handleInputChange}
            />
          </div>
        </div>
      </form>
    </div>
  );
}

export default InfoUpdate;
