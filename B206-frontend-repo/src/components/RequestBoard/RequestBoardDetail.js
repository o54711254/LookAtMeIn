import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import { useDispatch, useSelector } from "react-redux";

function ReqeustBoardDetail() {
  const [requestDetail, setRequestDetail] = useState([]);

  useEffect(() => {
    axiosApi
      .get(`api/requestBoardList/${requestDetail.request_board_seq}`)
      .then((res) => {
        setRequestDetail(res.data.responseObj);
      });
  }, []);

  const user = useSelector((state) => state.user);
  const isCoordinator = user.userSeq === "coordinator";

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
        {isCoordinator && (
          <>
            <button>수정</button>
            <button>삭제</button>
          </>
        )}
      </div>
      <div>{isCoordinator && <button>채팅 시작하기</button>}</div>
    </>
  );
}
export default ReqeustBoardDetail;
