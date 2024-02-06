import React, { Component } from 'react';

/**
 * OpenVidu를 사용하여 비디오 스트림을 표시하는 React 컴포넌트입니다.
 */
export default class OvVideoComponent extends Component {
    constructor(props) {
        super(props);
        // 비디오 요소에 대한 참조를 생성합니다.
        this.videoRef = React.createRef();
    }

    componentDidMount() {
        // 사용자의 스트림 관리자와 비디오 요소가 유효한 경우 비디오 요소를 추가합니다.
        if (this.props && this.props.user.streamManager && !!this.videoRef) {
            console.log('PROPS: ', this.props);
            this.props.user.getStreamManager().addVideoElement(this.videoRef.current);
        }

        // 사용자의 스트림 관리자 세션이 있고, 사용자 및 비디오 요소가 유효한 경우
        // 'signal:userChanged' 이벤트를 구독하고 이벤트가 발생할 때마다 새로운 스트림을 추가합니다.
        if (this.props && this.props.user.streamManager.session && this.props.user && !!this.videoRef) {
            this.props.user.streamManager.session.on('signal:userChanged', (event) => {
                const data = JSON.parse(event.data);
                if (data.isScreenShareActive !== undefined) {
                    this.props.user.getStreamManager().addVideoElement(this.videoRef.current);
                }
            });
        }
    }

    componentDidUpdate(props) {
        // 이전 props와 현재 props를 비교하여 변경 사항이 있는 경우에만 비디오 요소를 추가합니다.
        if (props && !!this.videoRef) {
            this.props.user.getStreamManager().addVideoElement(this.videoRef.current);
        }
    }

    render() {
        return (
            // 비디오 요소를 렌더링하고, autoPlay, id, ref, muted 등의 속성을 설정합니다.
            <video
                autoPlay={true}
                id={'video-' + this.props.user.getStreamManager().stream.streamId}
                ref={this.videoRef}
                muted={this.props.mutedSound}
            />
        );
    }
}
