import React, { useState, useEffect } from "react";
import axiosApi from "../../../api/axiosApi";
import { useSelector, useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import styles from "./InfoUpdate.module.css";
import IconButton from "@mui/material/IconButton";
import PhotoCamera from "@mui/icons-material/PhotoCamera";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";

function InfoUpdate() {
  const location = useLocation();
  const navigate = useNavigate();
  const user = useSelector((state) => state.user);
  const initialData = location.state || {};
  const [updateData, setUpdateData] = useState(initialData);
  const [selectedImage, setSelectedImage] = useState(null);

  useEffect(() => {
    if (!initialData.customerName) {
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
    const { name, value } = e.target;
    setUpdateData({
      ...updateData,
      [name]: value,
    });
  };

  const handleImageChange = (e) => {
    if (e.target.files[0]) {
      setSelectedImage(e.target.files[0]);
    }
  };

  const handleProfileImageUpdate = (event) => {
    event.preventDefault();
    if (!selectedImage) {
      alert("프로필 이미지를 선택해주세요.");
      return;
    }
    const formData = new FormData();

    formData.append("updateData", JSON.stringify(updateData));

    formData.append("uploadfile", selectedImage);

    axiosApi
      .put(`/api/mypage/user/${user.userSeq}`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then(() => {
        alert("프로필 사진이 업데이트 되었습니다.");
        navigate(`/mypage/info`);
      })
      .catch((error) => {
        console.error("프로필 사진 업데이트 중 에러 발생", error);
      });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // 여기서는 개인 정보만 업데이트하는 코드를 작성할 수 있습니다.
    // 프로필 사진 업데이트는 handleProfileImageUpdate 함수에서 처리합니다.
  };

  return (
    <div className={styles.infoContainer}>
      <form onSubmit={handleSubmit}>
        <div className={styles.infoTop}>
          <div className={styles.profileImgContainer}>
            <img src={selectedImage ? URL.createObjectURL(selectedImage) : profile} alt="profile" className={styles.profileImg} />
            <label htmlFor="icon-button-file">
              <input accept="image/*" id="icon-button-file" type="file" style={{ display: 'none' }} onChange={handleImageChange}/>
              <IconButton color="primary" aria-label="upload picture" component="span">
                <PhotoCamera />
              </IconButton>
            </label>
            <Button variant="contained" component="span" onClick={handleProfileImageUpdate}>
              프로필 사진 변경
            </Button>
          </div>
          <div className={styles.infoName}>
            {updateData.customerName}싸피 님 반갑습니다!
            <div className={styles.infoId}>ID: {updateData.userId}</div>
          </div>
        </div>
        {/* 사용자 정보 입력 필드 */}
        <TextField
          fullWidth
          margin="normal"
          label="이메일"
          name="customerEmail"
          type="email"
          value={updateData.customerEmail}
          onChange={handleInputChange}
        />
        <TextField
          fullWidth
          margin="normal"
          label="전화번호"
          name="customerPhoneNumber"
          value={updateData.customerPhoneNumber}
          onChange={handleInputChange}
        />
        <Button type="submit" variant="contained" color="primary">
          정보 업데이트
        </Button>
      </form>
    </div>
  );
}

export default InfoUpdate;
