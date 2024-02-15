import { createSlice } from "@reduxjs/toolkit";

// Redux Toolkit의 createSlice 함수를 사용하여 userSlice 생성
export const hospitalSlice = createSlice({
  name: "hospital", // 리듀서의 이름
  initialState: {
    profileImg: "",
    hospitalSeq: "",
    hospitalInfo_name: "",
    hospitalInfo_phoneNumber: "",
    hospitalInfo_email: "",
    hospitalInfo_introduce: "",
    hospitalInfo_address: "",
    hospitalInfo_open: "",
    hospitalInfo_close: "",
    hospitalInfo_url: "",
  }, // 초기 상태 값
  reducers: {
    loginHospital: (state, action) => {
      state.profileImg = action.payload.profileImg;
      state.hospitalSeq = action.payload.hospitalSeq;
      state.hospitalInfo_name = action.payload.hospitalInfo_name;
      state.hospitalInfo_phoneNumber = action.payload.hospitalInfo_phoneNumber;
      state.hospitalInfo_email = action.payload.hospitalInfo_email;
      state.hospitalInfo_introduce = action.payload.hospitalInfo_introduce;
      state.hospitalInfo_address = action.payload.hospitalInfo_address;
      state.hospitalInfo_open = action.payload.hospitalInfo_open;
      state.hospitalInfo_close = action.payload.hospitalInfo_close;
      state.hospitalInfo_url = action.payload.hospitalInfo_url;
      return state;
    },
    logoutHospital: (state) => {
      state.hospitalSeq = "";
      state.hospitalInfo_name = "";
      state.hospitalInfo_phoneNumber = "";
      state.hospitalInfo_email = "";
      state.hospitalInfo_introduce = "";
      state.hospitalInfo_address = "";
      state.hospitalInfo_open = "";
      state.hospitalInfo_close = "";
      state.hospitalInfo_url = "";
      return state;
    },
  },
});

// 생성된 액션들을 추출하여 내보냄
export const { loginHospital, logoutHospital } = hospitalSlice.actions;

// 생성된 리듀서를 내보냄
export default hospitalSlice.reducer;
