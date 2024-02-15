import socketClient from 'socket.io-client'

// import peerStore from '../peerStore/peerStore'
import store from '../redux/store'

import { setLocalStream,setRemoteStream } from '../peerStore/rtcSlice'

let socket;
let myPeer;
const SERVER = "http://localhost:5000"

export const connectWithWebSocket = ()=>{
    socket = socketClient(SERVER)
    console.log(socket)
    socket.on('connection',()=>{
        console.log('connected to server', socket.id)
        window.mySocketId = socket.id;
    })


}