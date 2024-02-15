import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../../../redux/user";
import axiosApi from "../../../api/axiosApi";
import styles from "./MyInfo.module.css";
import profile from "../../../assets/profile2.png";
import update from "../../../assets/update.png";
import { loginCustomer } from "../../../redux/customer";

// axios 완료
function MyInfo() {
  const user = useSelector((state) => state.user);

  const customer = useSelector((state) => state.customer);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [infoData, setInfoData] = useState({});
  const [profileImg, setProfileImg] = useState(null);
  const [selectedFile, setSelectedFile] = useState(null);
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
        // console.log("이미지", profileImg);
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
      "customerData",
      JSON.stringify({
        userId: infoData.userId,
        userPassword: user.userPassword,
        customerName: infoData.customerName,
        customerGender: infoData.customerGender,
        customerAddress: infoData.customerAddress,
        customerEmail: infoData.customerEmail,
        customerPhoneNumber: infoData.customerPhoneNumber,
      })
    );

    axiosApi
      .put(`/api/customer/mypage/modify/${user.userSeq}`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((res) => {
        console.log("프로필 이미지 업로드 성공", res);
        dispatch(
          loginCustomer({
            ...customer,
            profileImg: profileImg,
          })
        );
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
            {infoData.customerName} 님 반갑습니다!
            <div className={styles.infoId}>ID : {infoData.userId}</div>
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
