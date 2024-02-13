import { createSlice } from "@reduxjs/toolkit";

export const consultingSlice = createSlice({
  name: "consulting",
  initialState: {
    modalstate: false,
    userName: null,
    reservationSeq: null,
    reservationStart: null,
    dayOfWeek: null,
    hospitalSeq: null,
    userSeq: null,
  },
  reducers: {
    changeInfo: (state, action) => {
      state.modalstate = !state.modalstate;
      state.userName = action.payload.title;
      state.reservationSeq = action.payload.reservationSeq;
      state.reservationStart = action.payload.startDate;
      state.dayOfWeek = action.payload.dayOfWeek;
      state.userSeq = action.payload.userSeq;
      state.hospitalSeq = action.payload.hospitalSeq;
      console.log(action.payload);
      return state;
    },
    modalStateChange: (state) => {
      state.modalstate = !state.modalstate;
      return state;
    },
    // settingOperatingTime: (state, action) => {
    //   console.log(action.payload)
    //   return state
    // }
  },
});

export const { changeInfo, modalStateChange } = consultingSlice.actions;
export default consultingSlice.reducer;
