function Meeting() {
  return <div></div>;
}
export default Meeting;

// import React, { useEffect, useState } from "react";
// import { useLocation, useNavigate } from "react-router-dom";
// import { OpenVidu } from "openvidu-browser";

// import { useAuthStore } from "../app/store";
// import Container from "../components/Container";
// import Players from "../feature/multiplay/Players";
// import Sidebar from "../feature/multiplay/Sidebar";

// import styles from "./WaitingPage.module.css";

// const WaitingPage = () => {
//   const userId = useAuthStore((state) => state.user);
//   const navigate = useNavigate();
//   const location = useLocation();
//   const { sessionId, inviteCode, token, isModerator, userNickname } = location.state;
//   console.log(location.state);
//   const [OV, setOV] = useState(null);
//   const [session, setSession] = useState(null);
//   const [publisher, setPublisher] = useState(null);
//   const [subscribers, setSubscribers] = useState([]);

//   useEffect(() => {
//     const OVInstance = new OpenVidu();
//     setOV(OVInstance);
//     const sessionInstance = OVInstance.initSession();

//     sessionInstance.on("streamCreated", (event) => {
//       const subscriber = sessionInstance.subscribe(event.stream, undefined);
//       setSubscribers((prevSubscribers) => [...prevSubscribers, subscriber]);
//     });

//     sessionInstance.on("streamDestroyed", (event) => {
//       setSubscribers((prevSubscribers) => prevSubscribers.filter((sub) => sub !== event.stream.streamManager));
//     });

//     sessionInstance
//       .connect(token)
//       .then(() => {
//         const publisher = OVInstance.initPublisher(undefined, {
//           audioSource: undefined,
//           videoSource: undefined,
//           publishAudio: true,
//           publishVideo: true,
//           resolution: "640x480",
//           frameRate: 30,
//           insertMode: "APPEND",
//           mirror: false,
//         });
//         sessionInstance.publish(publisher);
//         setPublisher(publisher);
//       })
//       .catch((error) => console.log("There was an error connecting to the session:", error));

//     setSession(sessionInstance);

//     return () => {
//       if (sessionInstance) {
//         sessionInstance.disconnect();
//       }
//     };
//   }, [sessionId, token]);

//   const copyCode = () => {
//     // �띿뒪�몃� 蹂듭궗�섍린 �꾪븳 �꾩떆 �붿냼瑜� �앹꽦�⑸땲��.
//     var tempInput = document.createElement("input");
//     tempInput.value = inviteCode;

//     // �붿냼瑜� �섏씠吏��� 異붽��⑸땲��.
//     document.body.appendChild(tempInput);

//     // �낅젰 �붿냼瑜� �좏깮�섍퀬 蹂듭궗 紐낅졊�� �ㅽ뻾�⑸땲��.
//     tempInput.select();
//     document.execCommand("copy");

//     // �꾩떆 �붿냼瑜� �쒓굅�⑸땲��.
//     document.body.removeChild(tempInput);
//   };

//   const startQuiz = () => {
//     // subscribers 由ъ뒪�� 蹂대궡�� 遺�遺� 援ы쁽以�
//     // navigate(`../multiplay/start`, {
//     //   state: { publisher: publisher, subscribers: subscribers },
//     // });
//   };

//   return (
//     <Container>
//       <h1>WaitingPage : {sessionId}</h1>
//       <div className="flex">
//         <div className="w-4/6 p-1 border-4 border-violet-500">
//           <p>援щ룆�� : {subscribers.length}</p>
//           <Players publisher={publisher} subscribers={subscribers} />
//           <div className="flex justify-center">
//             <div className={styles.code} onClick={copyCode}>
//               {inviteCode}
//             </div>
//             {isModerator && (
//               <div onClick={startQuiz} className={styles.start}>
//                 �쒖옉�섍린
//               </div>
//             )}
//           </div>
//         </div>
//         <div className="w-2/6 h-[90vh] p-1 border-4 border-red-500">
//           <Sidebar isManager={isModerator} />
//         </div>
//       </div>
//     </Container>
//   );
// };

// export default WaitingPage;
