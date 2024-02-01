import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import { useDispatch, useSelector } from "react-redux";
import RequestUpdate from "./RequestDetailUpdate";
import { useNavigate, useParams } from "react-router-dom"; // 페이지 이동 및 URL 파라미터를 위한 훅 추가

function ReqeustBoardDetail() {
  const [requestDetail, setRequestDetail] = useState(null); // 초기 상태를 null로 설정
  const { requestId } = useParams(); // URL에서 requestId 값을 가져옴
  const dispatch = useDispatch(); // Redux의 dispatch 함수 사용
  const navigate = useNavigate(); // 페이지 이동을 위한 navigate 함수

  const user = useSelector((state) => state.user);
  const isCoordinator = user.userSeq === "coordinator";

  useEffect(() => {
    if (requestId) {
      // requestId가 있는 경우에만 API 호출
      axiosApi
        .get(`api/requestBoardList/${requestId}`)
        .then((res) => {
          setRequestDetail(res.data.responseObj);
        })
        .catch((error) => {
          console.error("상세 정보를 불러오는 중 오류 발생", error);
        });
    }
  }, [requestId]); // requestId 값이 변경될 때마다 useEffect 훅 실행
  // 상세 정보가 아직 로드되지 않은 경우 대비 처리
  if (!requestDetail) {
    return <div>Loading...</div>;
  }

  // isWriter를 확인하기 위해 현재 로그인한 유저의 ID와 게시글 작성자 ID 비교
  const isWriter = user.userId === requestDetail.customer_id;

  return (
    <>
      <div>
        <h3>상담 요청</h3>
        <p>
          여러 병원의 전문 코디네이터들이 화상상담을 활용하여 맞춤형 컨설팅을
          제공합니다.
        </p>
      </div>
      <div>
        <div>작성자 : {requestDetail.customer_id}</div>
        <div>제목: {requestDetail.requestBoard_title}</div>
        <div>해시태그: {requestDetail.requestBoard_hashTag}</div>
        <div>조회수: {requestDetail.requestBoard_cnt}</div>
        <div>작성날짜: {requestDetail.requestBoard_}</div>
        {isWriter && (
          <>
            <button
              onClick={() =>
                navigate(`api/RequestBoard/RequestdetailUpdate/${requestId}`)
              }
            >
              수정
            </button>
            <button
              onClick={() => {
                /* 삭제 로직 구현 */
              }}
            >
              삭제
            </button>
          </>
        )}
      </div>
      <div>{isCoordinator && <button>채팅 시작하기</button>}</div>
    </>
  );
}
export default ReqeustBoardDetail;
