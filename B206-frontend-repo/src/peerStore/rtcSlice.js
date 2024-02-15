import { createSlice } from "@reduxjs/toolkit";

const RtcSlice = createSlice({
    name: "RtcSlice",
    initialState: {
        remoteStream: null,
        localStream: null
    },
    reducers:{
        setRemoteStream: (state, action) => {
            state.remoteStream = action.payload;
        },

        setLocalStream: (state, action) => {
            state.localStream = action.payload;
        }
    }
})

export const {
    setLocalStream,setRemoteStream
} = RtcSlice.actions

export default RtcSlice.reducer