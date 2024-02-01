// src/components/RequestBoard/RequestDetailUpdate.js
import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import {
  setRequestDetail, // 게시글 상세 정보를 Redux Store에 저장하는 액션
  updateRequestDetail, // 게시글 상세 정보를 업데이트하는 액션
} from "../../redux/requestBoard"; // Redux 상태 관리 파일에서 액션 생성자 임포트

function RequestDetailUpdate() {
  const { requestBoard_seq } = useParams(); // 수정할 게시글의 seq url에서 추출함
  const navigate = useNavigate();
  const dispatch = useDispatch();
  // Redux Store에서 현재 게시글의 상세 정보(title, content) 가져오기
  const requestDetail = useSelector(
    (state) => state.requestBoard.requestDetail
  );
  // 컴포넌트 로컬 상태로 폼 데이터 관리, Redux Store의 상태를 초기값으로 사용
  const [formState, setFormState] = useState({
    title: requestDetail.title, // Redux Store에서 가져온 title
    content: requestDetail.content, // Redux Store에서 가져온 content
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // 여기에서 게시글 수정 API 호출
      await axiosApi.put(
        `api/requestBoard/${requestBoard_seq}/update`,
        formState
      );
      // 성공 시, 수정된 상태로 Redux Store 업데이트
      dispatch(updateRequestDetail({ ...formState }));
      // 수정 완료 후 상세 페이지로 리디렉션
      navigate(`api/requestBoard/${requestBoard_seq}`);
    } catch (error) {
      console.error("게시글 수정 중 오류 발생", error);
    }
  };
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormState((prevState) => ({ ...prevState, [name]: value }));
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="title">제목:</label>
        <input
          type="text"
          id="title"
          name="title"
          value={formState.title}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="content">내용:</label>
        <textarea
          id="content"
          name="content"
          value={formState.content}
          onChange={handleChange}
        />
      </div>
      <button type="submit">수정 완료</button>
    </form>
  );
}

export default RequestDetailUpdate;
