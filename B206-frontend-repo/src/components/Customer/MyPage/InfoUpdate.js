import { useSelector, useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axiosApi from "../../../api/axiosApi";
import styles from "./InfoUpdate.module.css"; // MyInfo의 CSS를 그대로 사용
import profile from "../../../assets/man/유승호.jpg"; // 프로필 이미지 경로
import check from "../../../assets/check.png";

function InfoUpdate() {
  const location = useLocation();
  const navigate = useNavigate();
  const user = useSelector((state) => state.user);
  const initialData = location.state || {};
  const [updateData, setUpdateData] = useState(initialData);

  useEffect(() => {
    if (!initialData.customerName) {
      // location.state가 비어있을 경우, 사용자 정보를 다시 로드
      axiosApi
        .get(`/api/mypage/${user.userSeq}`)
        .then((res) => {
          setUpdateData(res.data);
        })
        .catch((error) => {
          console.error("데이터를 불러오는 중 에러 발생", error);
        });
    }
  }, [user.userSeq, initialData]);

  const handleInputChange = (e) => {
    setUpdateData({
      ...updateData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    axiosApi
      .put(`/api/mypage/user/${user.userSeq}`, updateData)
      .then(() => {
        console.log(updateData)
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
          {updateData.customerName}싸피 님 반갑습니다!
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
              value={updateData.customerGender}
              onChange={handleInputChange}
            />
            <div className={styles.title}>주소</div>
            <input
              className={styles.contents}
              name="customerAddress"
              value={updateData.customerAddress}
              onChange={handleInputChange}
            />
            <div className={styles.title}>이메일</div>
            <input
              className={styles.contents}
              name="customerEmail"
              type="email"
              value={updateData.customerEmail}
              onChange={handleInputChange}
            />
            <div className={styles.title}>전화번호</div>
            <input
              className={styles.contents}
              name="customerPhoneNumber"
              value={updateData.customerPhoneNumber}
              onChange={handleInputChange}
            />
          </div>
        </div>
      </form>
    </div>
  );
}

export default InfoUpdate;
