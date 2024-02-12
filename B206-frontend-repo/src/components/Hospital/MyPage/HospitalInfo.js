import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axiosApi from "../../../api/axiosApi";
import update from "../../../assets/update.png";
import profile from "../../../assets/profile.png";
import styles from "./HospitalInfo.module.css";

function HospitalInfo() {
  const hospital = useSelector((state) => state.hospital);
  const navigate = useNavigate();
  const [infoData, setInfoData] = useState({});
  const [profileImg, setProfileImg] = useState(null);
  const [selectedFile, setSelectedFile] = useState(null);

  useEffect(() => {
    axiosApi
      .get(`/api/mypage/${hospital.userSeq}`)
      .then((res) => {
        console.log(hospital);
        setInfoData(res.data);
        const base64 = res.data.base64;
        const type = res.data.type;
        if (base64) {
          const data = `data:${type};base64,${base64}`;
          setProfileImg(data);
        } else {
          setProfileImg(profile);
        }
        console.log("이미지", profileImg);
      })
      .catch((error) => {
        console.log("데이터를 불러오는 중 에러 발생", error);
      });
  }, []);

  const onInfoUpdate = () => {
    navigate(`update`, { state: infoData });
  };
  const handleImageChange = (e) => {
    if (e.target.files && e.target.files[0]) {
      const file = e.target.files[0];
      setSelectedFile(file);
      setProfileImg(URL.createObjectURL(file)); // 미리보기를 위해 URL 생성
    }
  };

  const handleImageUpload = () => {
    const formData = new FormData();
    if (selectedFile) {
      formData.append("profile", selectedFile);
    }
    formData.append(
      "hospitalData",
      JSON.stringify({
        hospitalInfo_id: infoData.hospitalInfo_id,
        hospitalInfo_password: infoData.hospitalInfo_password,
        hospitalInfo_name: infoData.hospitalInfo_name,
        hospitalInfo_phoneNumber: infoData.hospitalInfo_phoneNumber,
        hospitalInfo_email: infoData.hospitalInfo_email,
        hospitalInfo_introudce: infoData.hospitalInfo_introudce,
        hospitalInfo_address: infoData.hospitalInfo_address,
        hospitalInfo_open: infoData.hospitalInfo_open,
        hospitalInfo_close: infoData.hospitalInfo_close,
        hospitalInfo_url: infoData.hospitalInfo_url,
      })
    );

    axiosApi
      .put(`/api/customer/mypage/modify/${hospital.userSeq}`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((res) => {
        console.log("프로필 이미지 업로드 성공", res);
        window.location.reload();
      })
      .catch((error) => {
        console.log("업로드중 에러 발생", error);
      });
  };
  return (
    <div className={styles.infoContainer}>
      <div className={styles.infoTop}>
        <label htmlFor="image-upload" className={styles.profileImgLabel}>
          <img
            src={profileImg}
            alt="profileImg"
            className={styles.profileImg}
          />
        </label>
        <input
          id="image-upload"
          type="file"
          accept="image/*"
          onChange={handleImageChange}
          style={{ display: "none" }}
        />
        <div className={styles.headBox}>
          <div className={styles.infoName}>
            {infoData.hospitalInfo_name} 님 반갑습니다!
            <div className={styles.infoId}>ID : {infoData.hospitalInfo_id}</div>
          </div>
          <div className={styles.profileButtonArea}>
            <div className={styles.버튼버튼}>
              {selectedFile && (
                <img
                  src={update}
                  alt="updateIcon"
                  className={styles.icon}
                  onClick={handleImageUpload}
                />
              )}
            </div>
          </div>
        </div>
      </div>
      <div className={styles.box}>
        <div className={styles.infoMiddle}>
          <div className={styles.info}>병원정보</div>
          <img
            src={update}
            alt="updateIcon"
            className={styles.icon}
            onClick={onInfoUpdate}
          />
        </div>
        <div className={styles.infoBottom}>
          <div className={styles.title}>병원명 </div>
          <div className={styles.contents}>{infoData.hospitalInfo_name}</div>
          <div className={styles.title}>주소</div>
          <div className={styles.contents}>{infoData.hospitalInfo_address}</div>
          <div className={styles.title}>이메일</div>
          <div className={styles.contents}>{infoData.hospitalInfo_email}</div>
          <div className={styles.title}>전화번호</div>
          <div className={styles.contents}>
            {infoData.hospitalInfo_phoneNumber}
          </div>
          <div className={styles.title}>운영시간</div>
          <div className={styles.contents}>
            {infoData.hospitalInfo_open} - {infoData.hospitalInfo_close}
          </div>
          <div className={styles.title}>홈페이지</div>
          <div className={styles.contents}>{infoData.hospitalInfo_url}</div>
        </div>
      </div>
    </div>
  );
}
export default HospitalInfo;
