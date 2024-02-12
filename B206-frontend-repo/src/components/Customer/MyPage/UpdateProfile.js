import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axiosApi from "../../../api/axiosApi";
import styles from "./MyInfo.module.css";
import profile from "../../../assets/man/유승호.jpg";
import update from "../../../assets/update.png"

function MyInfo() {
  const user = useSelector((state) => state.user);
  const navigate = useNavigate();
  const [infoData, setInfoData] = useState({});
  const [selectedImage, setSelectedImage] = useState(null); // 추가: 선택된 이미지를 저장할 상태

  useEffect(() => {
    axiosApi
      .get(`/api/mypage/${user.userSeq}`)
      .then((res) => {
        setInfoData(res.data);
      })
      .catch((error) => {
        console.log("데이터를 불러오는 중 에러 발생", error);
      });
  }, [user.userSeq]);

  const onInfoUpdate = () => {
    navigate(`update`, { state: infoData });
  };

  const handleImageChange = (e) => {
    if (e.target.files[0]) {
      const file = e.target.files[0];
      setSelectedImage(file);
      uploadProfileImage(file);
    }
  };

  const uploadProfileImage = (file) => {
    const formData = new FormData();
    formData.append("profileImage", file);
    // API 경로는 예시이며, 실제 프로젝트에 맞게 조정하세요.
    axiosApi
      .post(`/api/mypage/${user.userSeq}/uploadProfileImage`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        // 성공적으로 업로드 후 페이지를 새로고침하거나 사용자에게 알림
        console.log("프로필 이미지 업로드 성공", response.data);
        window.location.reload(); // 페이지 새로고침
      })
      .catch((error) => {
        console.error("프로필 이미지 업로드 실패", error);
      });
  };

  return (
    <div className={styles.infoContainer}>
      <div className={styles.infoTop}>
        {/* 프로필 이미지 변경을 위한 input type="file" 추가 */}
        <label htmlFor="profile-image-input" className={styles.profileImgLabel}>
          <img src={profile} alt="profileImg" className={styles.profileImg} />
          <input
            id="profile-image-input"
            type="file"
            accept="image/*"
            onChange={handleImageChange}
            style={{ display: "none" }}
          />
        </label>
        <div className={styles.infoName}>
          {infoData.customerName}싸피 님 반갑습니다!
          <div className={styles.infoId}>ID : {infoData.userId}</div>
        </div>
      </div>
      {/* 나머지 정보 표시 부분 */}
      <div className={styles.box}>
        <div className={styles.infoMiddle}>
          <div className={styles.info}>고객정보</div>
          <img src={update} alt="updateIcon" className={styles.icon} onClick={onInfoUpdate} />
        </div>
        <div className={styles.infoBottom}>
          {/* 고객 정보 표시 부분 */}
        </div>
      </div>
    </div>
  );
}

export default MyInfo;
