import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import React, { Component } from 'react';
import ChatComponent from './chat/ChatComponent';
import DialogExtensionComponent from './dialog-extension/DialogExtension';
import StreamComponent from './stream/StreamComponent';
import styles from './VideoRoomComponent.module.css';
import Button from "@mui/material/Button";
import Skeleton from "@mui/material/Skeleton";
import Link from "@mui/material/Link";
import { useSelector } from "react-redux"

import OpenViduLayout from '../layout/openvidu-layout';
import UserModel from '../models/user-model';
import ToolbarComponent from './toolbar/ToolbarComponent';
import Canvas from  "../../components/Canvas/canvas"

var localUser = new UserModel();
const APPLICATION_SERVER_URL = 'http://localhost:80/';

class VideoRoomComponent extends Component {
    constructor(props) {
      super(props);
      this.remotes = [];
      this.layout = new OpenViduLayout();
      this.hasBeenUpdated = false;
      this.localUserAccessAllowed = false;
      this.state = {
        mySessionId:
          this.props.who === "CUSTOMER"
            ? `u${this.props.userSeq}`
            : `p${this.props.hosSeq}`,
        myUserName: "Participant" + Math.floor(Math.random() * 100),
        session: undefined,
        localUser: undefined,
        mainStreamManager: undefined, // Main video of the page. Will be the 'publisher' or one of the 'subscribers'
        subscribers: [],
        entered: false,
        currentVideoDevice: undefined,
        showCanvas: false,
      };
  
      this.joinSession = this.joinSession.bind(this);
      this.leaveSession = this.leaveSession.bind(this);
      this.onbeforeunload = this.onbeforeunload.bind(this);
      this.updateLayout = this.updateLayout.bind(this);
      this.camStatusChanged = this.camStatusChanged.bind(this);
      this.micStatusChanged = this.micStatusChanged.bind(this);
      this.nicknameChanged = this.nicknameChanged.bind(this);
      this.toggleFullscreen = this.toggleFullscreen.bind(this);
      this.switchCamera = this.switchCamera.bind(this);
      this.screenShare = this.screenShare.bind(this);
      this.stopScreenShare = this.stopScreenShare.bind(this);
      this.closeDialogExtension = this.closeDialogExtension.bind(this);
      this.toggleChat = this.toggleChat.bind(this);
      this.checkNotification = this.checkNotification.bind(this);
      // this.checkSize = this.checkSize.bind(this);
      this.enteredChanged = this.enteredChanged.bind(this);
      this.toggleCanvas = this.toggleCanvas.bind(this);

    }
    componentDidMount() {
      const openViduLayoutOptions = {
        maxRatio: 3 / 2, // The narrowest ratio that will be used (default 2x3)
        minRatio: 9 / 16, // The widest ratio that will be used (default 16x9)
        fixedRatio: false, // If this is true then the aspect ratio of the video is maintained and minRatio and maxRatio are ignored (default false)
        bigClass: "OV_big", // The class to add to elements that should be sized bigger
        bigPercentage: 0.8, // The maximum percentage of space the big ones should take up
        bigFixedRatio: false, // fixedRatio for the big ones
        bigMaxRatio: 3 / 2, // The narrowest ratio to use for the big elements (default 2x3)
        bigMinRatio: 9 / 16, // The widest ratio to use for the big elements (default 16x9)
        bigFirst: true, // Whether to place the big one in the top left (true) or bottom right
        animate: true, // Whether you want to animate the transitions
      };
  
      this.layout.initLayoutContainer(
        document.getElementById("layout"),
        openViduLayoutOptions
      );
      window.addEventListener("beforeunload", this.onbeforeunload);
      window.addEventListener("resize", this.updateLayout);
      // window.addEventListener("resize", this.checkSize);
      this.joinSession();
    }
  
    componentWillUnmount() {
      window.removeEventListener("beforeunload", this.onbeforeunload);
      window.removeEventListener("resize", this.updateLayout);
      window.removeEventListener("resize", this.checkSize);
      this.leaveSession();
    }
  
    onbeforeunload(event) {
      this.leaveSession();
    }
  
    joinSession() {
      console.log("쪼인");
      this.OV = new OpenVidu();
  
      this.setState(
        {
          session: this.OV.initSession(),
        },
        async () => {
          this.subscribeToStreamCreated();
          await this.connectToSession();
        }
      );
    }
  
    async connectToSession() {
      if (this.props.token !== undefined) {
        console.log("token received: ", this.props.token);
        this.connect(this.props.token);
      } else {
        try {
          console.log("토큰get시도");
          console.log("여기까진 되는데 getToken이 안되는건가?")
          var token = await this.getToken();
          this.connect(token);
        } catch (error) {
          console.error(
            "There was an error getting the token:",
            error.code,
            error.message
          );
          if (this.props.error) {
            this.props.error({
              error: error.error,
              messgae: error.message,
              code: error.code,
              status: error.status,
            });
          }
          alert("There was an error getting the token:", error.message);
        }
      }
    }
  
    connect(token) {
      this.state.session
        .connect(token, { clientData: this.state.myUserName })
        .then(() => {
          this.connectWebCam();
        })
        .catch((error) => {
          if (this.props.error) {
            this.props.error({
              error: error.error,
              messgae: error.message,
              code: error.code,
              status: error.status,
            });
          }
          alert("There was an error connecting to the session:", error.message);
          console.log(
            "There was an error connecting to the session:",
            error.code,
            error.message
          );
        });
    }
  
    async connectWebCam() {
      await this.OV.getUserMedia({
        audioSource: undefined,
        videoSource: undefined,
      });
      var devices = await this.OV.getDevices();
      var videoDevices = devices.filter((device) => device.kind === "videoinput");
  
      let publisher = this.OV.initPublisher(undefined, {
        audioSource: undefined,
        videoSource: videoDevices[0].deviceId,
        publishAudio: localUser.isAudioActive(),
        publishVideo: localUser.isVideoActive(),
        resolution: "640x480",
        frameRate: 30,
        insertMode: "APPEND",
      });
  
      if (this.state.session.capabilities.publish) {
        publisher.on("accessAllowed", () => {
          this.state.session.publish(publisher).then(() => {
            this.updateSubscribers();
            this.localUserAccessAllowed = true;
            if (this.props.joinSession) {
              this.props.joinSession();
            }
          });
        });
      }
      localUser.setNickname(this.state.myUserName);
      localUser.setConnectionId(this.state.session.connection.connectionId);
      localUser.setScreenShareActive(false);
      localUser.setStreamManager(publisher);
      this.subscribeToUserChanged();
      this.subscribeToStreamDestroyed();
      this.sendSignalUserChanged({
        isScreenShareActive: localUser.isScreenShareActive(),
      });
  
      this.setState(
        { currentVideoDevice: videoDevices[0], localUser: localUser },
        () => {
          this.state.localUser.getStreamManager().on("streamPlaying", (e) => {
            this.updateLayout();
            publisher.videos[0].video.parentElement.classList.remove(
              "custom-class"
            );
          });
        }
      );
    }
    toggleCanvas() {
        this.setState(prevState => ({ showCanvas: !prevState.showCanvas }));
    }
  
    updateSubscribers() {
      var subscribers = this.remotes;
      this.setState(
        {
          subscribers: subscribers,
        },
        () => {
          if (this.state.localUser) {
            this.sendSignalUserChanged({
              isAudioActive: this.state.localUser.isAudioActive(),
              isVideoActive: this.state.localUser.isVideoActive(),
              nickname: this.state.localUser.getNickname(),
              isScreenShareActive: this.state.localUser.isScreenShareActive(),
            });
          }
          this.updateLayout();
        }
      );
    }
  
    leaveSession() {
      console.log("leave 시행");
      const mySession = this.state.session;
  
      if (mySession) {
        mySession.disconnect();
      }
  
      // Empty all properties...
      this.OV = null;
      this.setState({
        session: undefined,
        subscribers: [],
        mySessionId: `${this.props.userSeq}`,
        myUserName: "OpenVidu_User" + Math.floor(Math.random() * 100),
        localUser: undefined,
      });
      if (this.props.leaveSession) {
        this.props.leaveSession();
      }
    }
    camStatusChanged() {
      localUser.setVideoActive(!localUser.isVideoActive());
      localUser.getStreamManager().publishVideo(localUser.isVideoActive());
      this.sendSignalUserChanged({ isVideoActive: localUser.isVideoActive() });
      this.setState({ localUser: localUser });
    }
  
    micStatusChanged() {
      localUser.setAudioActive(!localUser.isAudioActive());
      localUser.getStreamManager().publishAudio(localUser.isAudioActive());
      this.sendSignalUserChanged({ isAudioActive: localUser.isAudioActive() });
      this.setState({ localUser: localUser });
    }
  
    nicknameChanged(nickname) {
      let localUser = this.state.localUser;
      localUser.setNickname(nickname);
      this.setState({ localUser: localUser });
      this.sendSignalUserChanged({
        nickname: this.state.localUser.getNickname(),
      });
    }
  
    deleteSubscriber(stream) {
      const remoteUsers = this.state.subscribers;
      const userStream = remoteUsers.filter(
        (user) => user.getStreamManager().stream === stream
      )[0];
      let index = remoteUsers.indexOf(userStream, 0);
      if (index > -1) {
        remoteUsers.splice(index, 1);
        this.setState({
          subscribers: remoteUsers,
        });
      }
    }
  
    subscribeToStreamCreated() {
      this.state.session.on("streamCreated", (event) => {
        const subscriber = this.state.session.subscribe(event.stream, undefined);
        // var subscribers = this.state.subscribers;
        subscriber.on("streamPlaying", (e) => {
          this.checkSomeoneShareScreen();
          subscriber.videos[0].video.parentElement.classList.remove(
            "custom-class"
          );
        });
        const newUser = new UserModel();
        newUser.setStreamManager(subscriber);
        newUser.setConnectionId(event.stream.connection.connectionId);
        newUser.setType("remote");
        const nickname = event.stream.connection.data.split("%")[0];
        newUser.setNickname(JSON.parse(nickname).clientData);
        this.remotes.push(newUser);
        if (this.localUserAccessAllowed) {
          this.updateSubscribers();
        }
      });
    }
  
    subscribeToStreamDestroyed() {
      // On every Stream destroyed...
      this.state.session.on("streamDestroyed", (event) => {
        // Remove the stream from 'subscribers' array
        this.deleteSubscriber(event.stream);
        setTimeout(() => {
          this.checkSomeoneShareScreen();
        }, 20);
        event.preventDefault();
        this.updateLayout();
      });
    }
  
    subscribeToUserChanged() {
      this.state.session.on("signal:userChanged", (event) => {
        let remoteUsers = this.state.subscribers;
        remoteUsers.forEach((user) => {
          if (user.getConnectionId() === event.from.connectionId) {
            const data = JSON.parse(event.data);
            console.log("EVENTO REMOTE: ", event.data);
            if (data.isAudioActive !== undefined) {
              user.setAudioActive(data.isAudioActive);
            }
            if (data.isVideoActive !== undefined) {
              user.setVideoActive(data.isVideoActive);
            }
            if (data.nickname !== undefined) {
              user.setNickname(data.nickname);
            }
            if (data.isScreenShareActive !== undefined) {
              user.setScreenShareActive(data.isScreenShareActive);
            }
          }
        });
        this.setState(
          {
            subscribers: remoteUsers,
          },
          () => this.checkSomeoneShareScreen()
        );
      });
    }
  
    updateLayout() {
      setTimeout(() => {
        this.layout.updateLayout();
      }, 20);
    }
  
    sendSignalUserChanged(data) {
      const signalOptions = {
        data: JSON.stringify(data),
        type: "userChanged",
      };
      this.state.session.signal(signalOptions);
    }
  
    toggleFullscreen() {
      const document = window.document;
      const fs = document.getElementById("container");
      if (
        !document.fullscreenElement &&
        !document.mozFullScreenElement &&
        !document.webkitFullscreenElement &&
        !document.msFullscreenElement
      ) {
        if (fs.requestFullscreen) {
          fs.requestFullscreen();
        } else if (fs.msRequestFullscreen) {
          fs.msRequestFullscreen();
        } else if (fs.mozRequestFullScreen) {
          fs.mozRequestFullScreen();
        } else if (fs.webkitRequestFullscreen) {
          fs.webkitRequestFullscreen();
        }
      } else {
        if (document.exitFullscreen) {
          document.exitFullscreen();
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen();
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen();
        } else if (document.webkitExitFullscreen) {
          document.webkitExitFullscreen();
        }
      }
    }
  
    async switchCamera() {
      try {
        const devices = await this.OV.getDevices();
        var videoDevices = devices.filter(
          (device) => device.kind === "videoinput"
        );
  
        if (videoDevices && videoDevices.length > 1) {
          var newVideoDevice = videoDevices.filter(
            (device) => device.deviceId !== this.state.currentVideoDevice.deviceId
          );
  
          if (newVideoDevice.length > 0) {
            // Creating a new publisher with specific videoSource
            // In mobile devices the default and first camera is the front one
            var newPublisher = this.OV.initPublisher(undefined, {
              audioSource: undefined,
              videoSource: newVideoDevice[0].deviceId,
              publishAudio: localUser.isAudioActive(),
              publishVideo: localUser.isVideoActive(),
              mirror: true,
            });
  
            //newPublisher.once("accessAllowed", () => {
            await this.state.session.unpublish(
              this.state.localUser.getStreamManager()
            );
            await this.state.session.publish(newPublisher);
            this.state.localUser.setStreamManager(newPublisher);
            this.setState({
              currentVideoDevice: newVideoDevice,
              localUser: localUser,
            });
          }
        }
      } catch (e) {
        console.error(e);
      }
    }
  
    screenShare() {
      const videoSource =
        navigator.userAgent.indexOf("Firefox") !== -1 ? "window" : "screen";
      const publisher = this.OV.initPublisher(
        undefined,
        {
          videoSource: videoSource,
          publishAudio: localUser.isAudioActive(),
          publishVideo: localUser.isVideoActive(),
          mirror: false,
        },
        (error) => {
          if (error && error.name === "SCREEN_EXTENSION_NOT_INSTALLED") {
            this.setState({ showExtensionDialog: true });
          } else if (error && error.name === "SCREEN_SHARING_NOT_SUPPORTED") {
            alert("Your browser does not support screen sharing");
          } else if (error && error.name === "SCREEN_EXTENSION_DISABLED") {
            alert("You need to enable screen sharing extension");
          } else if (error && error.name === "SCREEN_CAPTURE_DENIED") {
            alert("You need to choose a window or application to share");
          }
        }
      );
  
      publisher.once("accessAllowed", () => {
        this.state.session.unpublish(localUser.getStreamManager());
        localUser.setStreamManager(publisher);
        this.state.session.publish(localUser.getStreamManager()).then(() => {
          localUser.setScreenShareActive(true);
          this.setState({ localUser: localUser }, () => {
            this.sendSignalUserChanged({
              isScreenShareActive: localUser.isScreenShareActive(),
            });
          });
        });
      });
      publisher.on("streamPlaying", () => {
        this.updateLayout();
        publisher.videos[0].video.parentElement.classList.remove("custom-class");
      });
    }
  
    closeDialogExtension() {
      this.setState({ showExtensionDialog: false });
    }
  
    stopScreenShare() {
      this.state.session.unpublish(localUser.getStreamManager());
      this.connectWebCam();
    }
  
    checkSomeoneShareScreen() {
      let isScreenShared;
      // return true if at least one passes the test
      isScreenShared =
        this.state.subscribers.some((user) => user.isScreenShareActive()) ||
        localUser.isScreenShareActive();
      const openviduLayoutOptions = {
        maxRatio: 3 / 2,
        minRatio: 9 / 16,
        fixedRatio: isScreenShared,
        bigClass: "OV_big",
        bigPercentage: 0.8,
        bigFixedRatio: false,
        bigMaxRatio: 3 / 2,
        bigMinRatio: 9 / 16,
        bigFirst: true,
        animate: true,
      };
      this.layout.setLayoutOptions(openviduLayoutOptions);
      this.updateLayout();
    }
  
    toggleChat(property) {
      let display = property;
  
      if (display === undefined) {
        display = this.state.chatDisplay === "none" ? "block" : "none";
      }
      if (display === "block") {
        this.setState({ chatDisplay: display, messageReceived: false });
      } else {
        console.log("chat", display);
        this.setState({ chatDisplay: display });
      }
      this.updateLayout();
    }
  
    checkNotification(event) {
      this.setState({
        messageReceived: this.state.chatDisplay === "none",
      });
    }
  
    // 상담방 입장
    async enteredChanged() {
      this.remotes = [];
      this.setState({
        mySessionId: `p${this.props.hosSeq}u${this.props.userSeq}`,
        subscribers: [],
        entered: true,
      });

      console.log(this.state.mySessionId);
  
      await this.leaveSession();
      await this.joinSession();
    }
  

    render() {
        const mySessionId = this.state.mySessionId;
        const localUser = this.state.localUser;
        const isEntered = this.state.entered;
        var chatDisplay = { display: this.state.chatDisplay };
        const who = this.props.role;


        return (
            <div>
              {localUser !== undefined &&
                localUser.getStreamManager() !== undefined &&
                (isEntered ? (
                  <div>
                    <hr className={styles.hr} />
                    <div className={styles.divnext}>
                    {
    this.props.showCanvas && <Canvas />
}
                      {localUser !== undefined &&
                        localUser.getStreamManager() !== undefined && (
                          <div className={styles.me}>
                            {this.state.subscribers.map((sub, i) => (
                              <div key={i} className={styles.you}>
                                <StreamComponent
                                  user={sub}
                                  streamId={sub.streamManager.stream.streamId}
                                  isMe={false}
                                />
                              </div>
                            ))}
                            {this.state.subscribers.length === 0 ? (
                              <Skeleton variant="rounded" width={640} height={486} />
                            ) : null}
                            <div className={styles.right}>
                              <StreamComponent
                                user={localUser}
                                // 유저닉네임 설정가능?
                                handleNickname={this.nicknameChanged}
                                isMe={true}
                              />
                            </div>
                          </div>
                        )}
                    </div>
                  </div>
                ) : (
                    <div className={styles.div}>
                        <div className={styles.videobox}>
                    <StreamComponent user={localUser} isMe={"test"} />
                    </div>
                    <div className={styles.alert}>
                      {who === "CUSTOMER" ? (
                        <div className={styles.contentbox}>
                          <p className={styles.title}>
                             상담 이용 안내
                          </p>
                          <h1 className={styles.content}>1.  상담 결과는 외부에 공개 할 수 없으며, 위반 시 사이트 이용에 제한이 있을 수 있습니다.</h1>
                          <p className={styles.content}>
                          2. 상담사에게 폭언 및 욕설 등 부적절한 발언 시 처벌 받을 수 있습니다.
                          </p>
                          <p className={styles.content}>
                          3. 본 상담을 통해 받은 견적은 실제 시술 비용과 상이할 수 있습니다.
                          </p>
                          <p className={styles.content}>
                          4. 카메라와 마이크 세팅이 끝났다면 아래의 입장하기 버튼을 눌러 상담에 입장해 주세요.
                          </p>
                          <div className={styles.enterbox}>
                          <button onClick={() => this.enteredChanged()} className={styles.enter}>
  입장하기
</button>

                      </div>
                        </div>
                      ) : (
                        <div className={styles.contentbox}>
                          <p className={styles.title}>
                             상담 이용 안내
                          </p>
                          <h1 className={styles.content}>1.  상담 결과는 외부에 공개 할 수 없으며, 위반 시 사이트 이용에 제한이 있을 수 있습니다.</h1>
                          <p className={styles.content}>
                          2. 고객에게 폭언 및 욕설 등 부적절한 발언 시 처벌 받을 수 있습니다.
                          </p>
                          <p className={styles.content}>
                          3. 고객에게 과도한 과장 광고 및 부적절한 정보 전달은 제재의 대상이 될 수 있습니다.
                          </p>
                          <p className={styles.content}>
                          4. 카메라와 마이크 세팅이 끝났다면 아래의 입장하기 버튼을 눌러 상담에 입장해 주세요.
                          </p>
                      <div className={styles.enterbox}>
                      <button onClick={() => this.enteredChanged()} className={styles.enter}>
                         입장하기
                        </button>

                      </div>
                        </div>
                      )}
      
                    </div>
                  </div>
                ))}
      
              <div className={styles.toolbar}>
                <ToolbarComponent
                  reservationSeq={this.props.reservationSeq}
                  who={this.props.role}
                  sessionId={mySessionId}
                  user={localUser}
                  showNotification={this.state.messageReceived}
                  camStatusChanged={this.camStatusChanged}
                  micStatusChanged={this.micStatusChanged}
                  screenShare={this.screenShare}
                  stopScreenShare={this.stopScreenShare}
                  toggleFullscreen={this.toggleFullscreen}
                  switchCamera={this.switchCamera}
                  leaveSession={this.leaveSession}
                  toggleChat={this.toggleChat}
                  toggleCanvas={this.toggleCanvas} // Canvas 토글 함수
                  showCanvas={this.state.showCanvas} // Canvas 상태
                />
              </div>
            </div>
          );
    }
    
    async getToken() {
        console.log("여기까진 오케")
        console.log(this.state.mySessionId)
        const sessionId = await this.createSession(this.state.mySessionId);
        console.log(sessionId);
        console.log("토큰도 생성됐어요!!!")
        return await this.createToken(sessionId);
      }
    
      async createSession(sessionId) {
        const response = await axios.post(
            APPLICATION_SERVER_URL + "api/sessions",
            { customSessionId: sessionId },
            {
                headers: { "Content-Type": "application/json" },
            }
            );
        return response.data; // The sessionId to getToken()
      }
    
      async createToken(sessionId) {
        const response = await axios.post(
          APPLICATION_SERVER_URL + "api/sessions/" + sessionId + "/connections",
          {},
          {
            headers: { "Content-Type": "application/json" },
          }
        );
        return response.data; // The token
      }
}
export default VideoRoomComponent;
