// src/redux/slices/requestBoardSlice.js
import { createSlice } from "@reduxjs/toolkit";

export const requestBoardSlice = createSlice({
  name: "requestBoard",
  initialState: {
    requestDetail: {
      title: "",
      content: "",
    },
  },
  reducers: {
    // 게시글 상세 정보 설정
    setRequestDetail: (state, action) => {
      state.requestDetail = action.payload;
    },
    // 게시글 상세 정보 업데이트
    updateRequestDetail: (state, action) => {
      const { title, content, images } = action.payload;
      if (title !== undefined) state.requestDetail.title = title;
      if (content !== undefined) state.requestDetail.content = content;
      //   if (images !== undefined) state.requestDetail.images = images;
    },
    // 게시글 상세 정보 초기화
    clearRequestDetail: (state) => {
      state.requestDetail = {
        title: "",
        content: "",
        images: [],
      };
    },
  },
});

export const { setRequestDetail, updateRequestDetail, clearRequestDetail } =
  requestBoardSlice.actions;
export default requestBoardSlice.reducer;
