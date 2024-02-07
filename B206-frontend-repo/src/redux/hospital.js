import { createSlice } from "@reduxjs/toolkit";

// Redux Toolkit의 createSlice 함수를 사용하여 userSlice 생성
export const hospitalSlice = createSlice({
  name: "hospital", // 리듀서의 이름
  initialState: {
    userSeq: "", 
    hospitalName: "",
  }, // 초기 상태 값
  reducers: {
    setHospital: (state, action) => {
      state.hospitalSeq = action.payload.userSeq; // 사용자 일련번호 업데이트
      state.hospitalName = action.payload.hospitalName; // 사용자 이름 업데이트
      return state;
    },
  },
});

// 생성된 액션들을 추출하여 내보냄
export const { setHospital } = hospitalSlice.actions;

// 생성된 리듀서를 내보냄
export default hospitalSlice.reducer;
