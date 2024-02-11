import { createSlice } from "@reduxjs/toolkit";

// Redux Toolkit의 createSlice 함수를 사용하여 userSlice 생성
export const userSlice = createSlice({
  name: "user", // 리듀서의 이름
  initialState: {
    userSeq: "", // 사용자 역할
    profileImg: "",
    userId: "", // 사용자 아이디
    userName: "", // 사용자 이름
    userPassword: "",
    userGender: "",
    userBirth: "",
    userPhone: "",
    userAdress: "",
    userEmail: "",
    role: "", // 역할 업데이트(customer/admin/cordinator/hospital)
  }, // 초기 상태 값
  reducers: {
    // 액션과 함께 호출될 리듀서 함수들
    // 로그인 성공 시 사용자 정보를 업데이트하는 액션
    loginUser: (state, action) => {
      console.log(action.payload);
      // action.payload에는 업데이트할 사용자 정보가 담겨 있음
      state.userSeq = action.payload.userSeq; // 사용자 일련번호 업데이트
      state.profileImg = action.payload.profileImg; // 프로필 사진
      state.userName = action.payload.userName; // 사용자 이름 업데이트
      state.userId = action.payload.userId; // 사용자 아이디 업데이트
      state.userPassword = action.payload.userPassword; // 사용자 비밀번호 업데이트
      state.userGender = action.payload.userGender;
      state.userBirth = action.payload.userBirth;
      state.userPhone = action.payload.userPhone;
      state.userAddress = action.payload.userAddress;
      state.userEmail = action.payload.userEmail;
      state.role = action.payload.role; // 관리자 여부 업데이트
      return state;
    },
    // 로그아웃 시 사용자 정보를 초기화하는 액션
    logoutUser: (state) => {
      state.userSeq = ""; // 사용자 일련번호 초기화
      state.userName = ""; // 사용자 이름 초기화
      state.userId = ""; // 사용자 아이디 초기화
      state.userPassword = ""; // 사용자 비밀번호 초기화
      state.userGender = ""; // 사용자 성별
      state.userBirth = ""; // 생일
      state.userPhone = ""; // 전화번호
      state.userAddress = ""; // 주소
      state.userEmail = ""; // 이메일
      state.role = ""; // 관리자 여부 초기화
      return state;
    },
  },
});

// 생성된 액션들을 추출하여 내보냄
export const { loginUser, logoutUser } = userSlice.actions;

// 생성된 리듀서를 내보냄
export default userSlice.reducer;
