import { useEffect, useRef } from "react"
import { useSelector } from "react-redux"
import * as RTC from "../../utils"
const Peer = ()=>{
    const {localStream, remoteStream} = useSelector((state)=>state.rtc)

    const localVideoRef = useRef();
    const remoteVideoRef = useRef();

    useEffect(()=>{
        RTC.connectWithWebSocket()
    },[])

    useEffect(()=>{
        console.log('ls', localStream)
    },[localStream])

    useEffect(()=>{
        console.log('rs', remoteStream)
    },[remoteStream])


    return (
        <div className="Peer">
            <div>
                local video
                <video autoPlay muted with={400} height={400} ref={localVideoRef} />
            </div>

            <div>
                remote video
                <video autoPlay muted with={400} height={400} ref={remoteVideoRef} />
            </div>
        </div>
    )
}

export default Peer