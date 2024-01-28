import { createSlice } from "@reduxjs/toolkit";

// 액세스 토큰 만료 시간 (밀리초 단위)
export const TOKEN_TIME_OUT = 600 * 1000;

// 액세스 토큰과 관련된 Redux slice를 생성합니다.
export const tokenSlice = createSlice({
  name: "authToken", // slice의 이름을 지정합니다.
  initialState: {
    authenticated: false, // 인증 여부를 나타내는 상태값입니다. 초기값은 false입니다.
    accessToken: null, // 액세스 토큰을 저장하는 상태값입니다. 초기값은 null입니다.
    loading: true, // 데이터 로딩 상태를 나타내는 상태값입니다. 초기값은 true입니다.
  },
  reducers: {
    setToken: (state, action) => {
      state.authenticated = true; // 현재 로그인 상태를 true로 변경합니다.
      state.accessToken = action.payload.accessToken; // 전달된 액션의 payload에서 accessToken을 가져와 상태값에 저장합니다.
    },
    deleteToken: (state) => {
      state.authenticated = false; // 로그아웃 시 인증 상태를 false로 변경합니다.
      state.accessToken = null; // 액세스 토큰을 null로 초기화합니다.
    },
    changeLoading: (state) => {
      state.loading = false; // 로딩 상태를 false로 변경하여 데이터 로딩이 완료되었음을 나타냅니다.
    },
  },
});

// 생성된 slice에서 생성된 액션들을 추출합니다.
export const { setToken, deleteToken, changeLoading } = tokenSlice.actions;

// slice의 reducer를 내보냅니다.
export default tokenSlice.reducer;
