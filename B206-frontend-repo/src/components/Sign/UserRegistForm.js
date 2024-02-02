import React from "react";
import { Link } from "react-router-dom";
import { useState } from "react";
import { useFormik } from "formik";
import * as yup from "yup";
import axiosApi from "../../api/axiosApi";
import logo from "../../assets/lab_logo.png";
import { useNavigate } from "react-router-dom";
import DaumPostcode from "react-daum-postcode";
import { Modal, Button } from "antd";
// import { BrowserRouter, Routes, Route } from "react-router-dom";

//회원가입 시 입력하는 정보 유효성 검사
const validationSchema = yup.object({
  id: yup.string().required("아이디를 입력하세요."),
  password: yup
    .string()
    .matches(
      /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*+=-]).{8,16}$/,
      "영문+숫자+특수문자 조합으로 8~16글자를 입력하세요."
    )
    .required("비밀번호를 입력하세요."),
  confirmPassword: yup
    .string()
    .oneOf([yup.ref("password"), null], "비밀번호가 일치하지 않습니다.")
    .required("비밀번호를 다시 입력하세요."),
  email: yup
    .string()
    .email("올바른 이메일 형식을 입력하세요.")
    .required("이메일을 입력하세요."),
  phoneNumber: yup
    .string()
    .matches(/^[0-9]+$/, "숫자만 입력하세요.")
    .required("전화번호를 입력하세요."),
  birth: yup
    .string()
    .matches(
      /^(19|20)\d\d\.(0[1-9]|1[0-2])\.(0[1-9]|[12][0-9]|3[01])$/,
      "생년월일을 올바른 형식으로 입력하세요 (yyyy.mm.dd)."
    )
    .required("생년월일을 입력하세요."),
});

function UserRegistForm() {
  // 페이지 간 이동을 담당하는 함수. (해당 경로로 페이지가 이동함)
  const navigate = useNavigate();
  //사용 가능한 정보인지 검사
  const [useable, setUseable] = useState(null);

  //아이디 중복체크
  const checkDuplicateId = (e) => {
    e.preventDefault();
    axiosApi.get(`/api/user/regist/idcheck/${formik.values.userId}`).then((response) => {
      console.log(response.data);
      if (response.data === true) {
        //alert("사용 불가한 아이디입니다."); //이미 사용중인 아이디
        setUseable(false);
      } else if (response.data === false) {
        //alert("사용 가능한 아이디입니다.");
        setUseable(true);
      } else {
        alert("사용 불가한 아이디입니다.");
      }
    });
  };

  const formik = useFormik({
    initialValues: {
      userId: "",
      userPassword: "",
      confirmPassword: "",
      customerName: "",
      customerGender: "",
      customerBirth: "",
      customerPhoneNumber: "",
      customerEmail: "",
      customerAddress: "",
    },
    validationSchema,
    onSubmit: async (values) => {
      const {
        userId,
        userPassword,
        // confirmPassword,
        customerName,
        customerGender,
        // customerBirth,
        customerPhoneNumber,
        customerEmail,
        customerAddress,
      } = values;
      console.log(values);
      if (!useable) {
        window.alert("아이디가 중복됩니다. 다시 시도해주세요.");
      } else {
        try {
          await axiosApi.post("/api/customer/regist", {
          });
          window.alert("회원가입이 완료되었습니다.");
          setTimeout(() => {
            navigate("/login"); //로그인창으로 바로 이동
          }, 3000);
        } catch {}
      }
    },
  });

  // 비밀번호 일치 체크
  const isPasswordMatch =
    formik.values.userPassword === formik.values.confirmPassword &&
    formik.values.userPassword !== "";

  //우편주소 api 사용한 부분

  //modalVisible은 모달이 보여질지 말지를 결정하는 불리언 값
  //setModalVisible 함수는 이 값을 변경하는 데 사용(showModal, closeModal에서 사용함)
  const [modalVisible, setModalVisible] = useState(false);
  const handleAddress = (data) => {
    let fullAddress = data.address;
    let extraAddress = "";

    if (data.addressType === "R") {
      if (data.bname !== "") {
        extraAddress += data.bname;
      }
      if (data.buildingName !== "") {
        extraAddress +=
          extraAddress !== "" ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== "" ? ` (${extraAddress})` : "";
    }

    formik.setFieldValue("customerAddress", fullAddress);
    setModalVisible(false); // 모달 닫기
  };
  //주소 입력 버튼을 클릭했을 때 모달을 보여주기
  const showModal = () => {
    setModalVisible(true);
  };
  //모달을 닫을 때 사용되며, modalVisible을 false로 설정
  const closeModal = () => {
    setModalVisible(false);
  };

  return (
    <>
      <div className="UserRegistForm">
        <div>
          <h1>룩앳미인</h1>
        </div>
        <form onSubmit={formik.handleSubmit}>
          <div className="inputText">
            <h3>아이디</h3>
            <input
              type="text"
              placeholder="abcd1234"
              name="userId"
              value={formik.values.userId}
              onChange={formik.handleChange}
              className={formik.touched.userId && formik.errors.userId ? "error" : ""}
            />
            {formik.touched.userId && formik.errors.userId && (
              <div className="helperText">{formik.errors.userId}</div>
            )}

            <button onClick={checkDuplicateId}>중복확인</button>
            {useable === null ? (
              ""
            ) : useable ? (
              <span>사용가능한 아이디입니다.</span>
            ) : (
              <span>이미 사용중인 아이디 입니다.</span>
            )}
          </div>
          <div className="inputText">
            <h3>비밀번호</h3>
            <input
              name="userPassword"
              type="password"
              onChange={formik.handleChange}
              placeholder="영문, 숫자, 특수문자 조합 8~16자를 입력하세요"
              value={formik.values.userPassword}
              className={
                formik.touched.userPassword && formik.errors.userPassword ? "error" : ""
              }
            />
            {formik.touched.userPassword && formik.errors.userPassword && (
              <div className="helperText">{formik.errors.userPassword}</div>
            )}
          </div>
          <div className="inputText">
            <h3>비밀번호 확인</h3>
            <input
              type="password"
              name="confirmPassword"
              value={formik.values.confirmPassword}
              onChange={formik.handleChange}
              placeholder="비밀번호 확인"
              style={{ color: isPasswordMatch ? "green" : "inherit" }} // 조건부 스타일 적용
            />
            {isPasswordMatch && (
              <div style={{ color: "green" }}>비밀번호가 일치합니다.</div>
            )}
          </div>
          <div className="inputText">
            <h3>이름</h3>
            <input
              type="text"
              placeholder="한글 이름"
              name="customerName"
              value={formik.values.customerName}
              onChange={formik.handleChange}
              className={
                formik.touched.customerName && formik.errors.customerName ? "error" : ""
              }
            />
            {formik.touched.customerName && formik.errors.customerName && (
              <div className="helperText">{formik.errors.customerName}</div>
            )}
          </div>
          <div className="inputText">
            <h3>성별</h3>
            <div>
              <label>
                <input
                  type="radio"
                  name="customerGender"
                  value="남"
                  checked={formik.values.customerGender === "남"}
                  onChange={formik.handleChange}
                />
                남
              </label>
            </div>
            <div>
              <label>
                <input
                  type="radio"
                  name="customerGender"
                  value="여"
                  checked={formik.values.customerGender === "여"}
                  onChange={formik.handleChange}
                />
                여
              </label>
            </div>
            {formik.touched.customerGender && formik.errors.customerGender && (
              <div className="helperText">{formik.errors.customerGender}</div>
            )}
          </div>
          <div className="inputText">
            <h3>생년월일</h3>
            <input
              type="text"
              placeholder="YYYY.MM.DD"
              name="customerBirth"
              value={formik.values.customerBirth}
              onChange={formik.handleChange}
              className={
                formik.touched.customerBirth && formik.errors.customerBirth ? "error" : ""
              }
            />
            {formik.touched.customerBirth && formik.errors.customerBirth && (
              <div className="helperText">{formik.errors.customerBirth}</div>
            )}
          </div>
          <div className="inputText">
            <h3>전화번호</h3>
            <input
              type="text"
              placeholder="01000000000"
              name="customerPhoneNumber"
              value={formik.values.customerPhoneNumber}
              onChange={formik.handleChange}
              className={
                formik.touched.customerPhoneNumber && formik.errors.customerPhoneNumber
                  ? "error"
                  : ""
              }
            />
            {formik.touched.customerPhoneNumber && formik.errors.customerPhoneNumber && (
              <div className="helperText">{formik.errors.customerPhoneNumber}</div>
            )}
          </div>
          <div className="inputText">
            <h3>주소</h3>
            <input
              type="text"
              name="customerAddress"
              value={formik.values.customerAddress}
              onChange={formik.handleChange}
              readOnly
            />
            <button type="button" onClick={showModal}>
              주소 입력
            </button>

            <Modal
              title="주소 검색"
              open={modalVisible}
              onCancel={closeModal}
              footer={null}
            >
              <DaumPostcode onComplete={handleAddress} />
            </Modal>
          </div>
          <div className="inputText">
            <h3>이메일</h3>
            <input
              type="text"
              placeholder="abcdef@ssafy.com"
              name="customerEmail"
              value={formik.values.customerEmail}
              onChange={formik.handleChange}
              className={
                formik.touched.customerEmail && formik.errors.customerEmail ? "error" : ""
              }
            />
            {formik.touched.customerEmail && formik.errors.customerEmail && (
              <div className="helperText">{formik.errors.customerEmail}</div>
            )}
          </div>
          <div className="RegistButton">
            <button type="submit">회원가입</button>
          </div>
        </form>
      </div>
    </>
  );
}
export default UserRegistForm;
