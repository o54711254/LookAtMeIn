import { createSlice } from "@reduxjs/toolkit";

// Redux Toolkit의 createSlice 함수를 사용하여 userSlice 생성
export const customerSlice = createSlice({
  name: "customer", // 리듀서의 이름
  initialState: {
    customerSeq: "", // 사용자 역할
    profileImg: "",
    customerName: "", // 사용자 이름
    customerGender: "",
    customerBirth: "",
    customerPhone: "",
    customerAdress: "",
    customerEmail: "",
  }, // 초기 상태 값
  reducers: {
    // 액션과 함께 호출될 리듀서 함수들
    // 로그인 성공 시 사용자 정보를 업데이트하는 액션
    loginCustomer: (state, action) => {
      state.customerSeq = action.payload.customerSeq; // 프로필 사진
      state.profileImg = action.payload.profileImg; // 프로필 사진
      state.customerName = action.payload.customerName; // 사용자 이름 업데이트
      state.customerGender = action.payload.customerGender;
      state.customerBirth = action.payload.customerBirth;
      state.customerPhone = action.payload.customerPhone;
      state.customerAddress = action.payload.customerAddress;
      state.customerEmail = action.payload.customerEmail;
      return state;
    },
    // 로그아웃 시 사용자 정보를 초기화하는 액션
    logoutCustomer: (state) => {
      state.customerSeq = ""; // 사용자 일련번호 초기화
      state.customerName = ""; // 사용자 이름 초기화
      state.customerGender = ""; // 사용자 성별
      state.customerBirth = ""; // 생일
      state.customerPhone = ""; // 전화번호
      state.customerAddress = ""; // 주소
      state.customerEmail = ""; // 이메일
      return state;
    },
  },
});

// 생성된 액션들을 추출하여 내보냄
export const { loginCustomer, logoutCustomer } = customerSlice.actions;

// 생성된 리듀서를 내보냄
export default customerSlice.reducer;
