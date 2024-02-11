import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axiosApi from "../../../api/axiosApi";
import styles from "./InfoUpdate.module.css";
import profile from "../../../assets/man/유승호.jpg";
import check from "../../../assets/check.png";

function InfoUpdate() {
  const navigate = useNavigate();
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
  }, [user.userSeq]); // userSeq가 변경될 때마다 데이터를 다시 불러옵니다.

  const handleInputChange = (e) => {
    setUpdateData({
      ...updateData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const hasChanges = Object.keys(initialData).some(
      (key) => updateData[key] !== initialData[key]
    );
    const dataToSubmit = hasChanges ? updateData : initialData;
    console.log("수정요청보낼데이터", dataToSubmit);
    axiosApi
      .put(`/api/customer/mypage/modify/${user.userSeq}`, dataToSubmit)
      .then(() => {
        navigate(`/mypage/info`);
      })
      .catch((error) => {
        console.error("유저 정보 수정 중 에러 발생", error);
      });
  };

  return (
    <div className={styles.infoContainer}>
      <div className={styles.infoTop}>
        <img src={profile} alt="profileImg" className={styles.profileImg} />
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
