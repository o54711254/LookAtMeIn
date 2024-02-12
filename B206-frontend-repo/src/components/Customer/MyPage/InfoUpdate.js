import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { loginUser } from "../../../redux/user";
import axiosApi from "../../../api/axiosApi";
import styles from "./InfoUpdate.module.css";
import profile from "../../../assets/man/유승호.jpg"; // 데모 이미지로 가정
import check from "../../../assets/check.png"; // 데모 체크 이미지로 가정

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
  const user = useSelector((state) => state.user);
  const [initialData, setInitialData] = useState({});
  const [updateData, setUpdateData] = useState({});

  useEffect(() => {
    axiosApi
      .get(`/api/mypage/${user.userSeq}`)
      .then((res) => {
        setInitialData(res.data);
        setUpdateData(res.data);
      })
      .catch((error) => {
        console.error("데이터를 불러오는 중 에러 발생", error);
      });
  }, [user.userSeq]);

  const handleInputChange = (e) => {
    setUpdateData({
      ...updateData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    // Base64 이미지 데이터에서 실제 데이터만 추출
    const base64Data = user.profileImg.split(",")[1];
    const imageBlob = base64ToBlob(base64Data, "image/png");

    const formData = new FormData();
    formData.append("profile", imageBlob, "profile.png");
    formData.append(
      "customerData",
      JSON.stringify({
        userId: updateData.userId,
        userPassword: updateData.userPassword,
        customerName: updateData.customerName,
        customerGender: updateData.customerGender,
        customerAddress: updateData.customerAddress,
        customerEmail: updateData.customerEmail,
        customerPhoneNumber: updateData.customerPhoneNumber,
      })
    );

    axiosApi
      .put(`/api/customer/mypage/modify/${user.userSeq}`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((res) => {
        console.log("수정성공", res);
        dispatch(
          loginUser({
            ...user,
            ...updateData,
            profileImg: user.profileImg, // 기존 이미지 정보를 유지
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
          src={user.profileImg}
          alt="profileImg"
          className={styles.profileImg}
        />
        <div className={styles.infoName}>
          {updateData.customerName}님 반갑습니다!
          <div className={styles.infoId}>ID : {updateData.userId}</div>
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
            <div className={styles.title}>성별</div>
            <input
              className={styles.contents}
              name="customerGender"
              value={updateData.customerGender || ""}
              onChange={handleInputChange}
            />
            <div className={styles.title}>주소</div>
            <input
              className={styles.contents}
              name="customerAddress"
              value={updateData.customerAddress || ""}
              onChange={handleInputChange}
            />
            <div className={styles.title}>이메일</div>
            <input
              className={styles.contents}
              name="customerEmail"
              type="email"
              value={updateData.customerEmail || ""}
              onChange={handleInputChange}
            />
            <div className={styles.title}>전화번호</div>
            <input
              className={styles.contents}
              name="customerPhoneNumber"
              value={updateData.customerPhoneNumber || ""}
              onChange={handleInputChange}
            />
          </div>
        </div>
      </form>
    </div>
  );
}

export default InfoUpdate;
