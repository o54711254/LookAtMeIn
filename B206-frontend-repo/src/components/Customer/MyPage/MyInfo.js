import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axiosApi from "../../../api/axiosApi";
import styles from "./MyInfo.module.css";
import profile from "../../../assets/profile2.png";
import update from "../../../assets/update.png";

// axios 완료
function MyInfo() {
  const user = useSelector((state) => state.user);
  const navigate = useNavigate();
  const [infoData, setInfoData] = useState({});
  const [profileImg, setProfileImg] = useState(null);
  useEffect(() => {
    axiosApi
      .get(`/api/mypage/${user.userSeq}`)
      .then((res) => {
        console.log(res.data);
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
  return (
    <div className={styles.infoContainer}>
      <div className={styles.infoTop}>
        <img src={profileImg} alt="profileImg" className={styles.profileImg} />
        <div className={styles.infoName}>
          {infoData.customerName} 님 반갑습니다!
          <div className={styles.infoId}>ID : {infoData.userId}</div>
        </div>
      </div>
      <div className={styles.box}>
        <div className={styles.infoMiddle}>
          <div className={styles.info}>고객정보</div>
          <img
            src={update}
            alt="updateIcon"
            className={styles.icon}
            onClick={onInfoUpdate}
          />
        </div>
        <div className={styles.infoBottom}>
          <div className={styles.title}>성별 </div>
          <div className={styles.contents}>{infoData.customerGender}</div>
          <div className={styles.title}>주소</div>
          <div className={styles.contents}>{infoData.customerAddress}</div>
          <div className={styles.title}>이메일</div>
          <div className={styles.contents}>{infoData.customerEmail}</div>
          <div className={styles.title}>전화번호</div>
          <div className={styles.contents}>{infoData.customerPhoneNumber}</div>
        </div>
      </div>
    </div>
  );
}
export default MyInfo;
