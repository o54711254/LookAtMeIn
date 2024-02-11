import React, { useState, useEffect } from "react";
import axiosApi from "../../api/axiosApi";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function RequestRegist() {
  const [requestBoardData, setRequestBoardData] = useState({
    requestBoardData_title: "",
    requestBoardData_content: "",
    requestBoardData_part: "", //시술 부위 여러개면 배열로?
    requestBoardData_region: "",
    //상담 요청 사진은?
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setRequestBoardData({ ...requestBoardData, [name]: value });
  };
  const navigate = useNavigate();
  const handleSubmit = (e) => {
    e.preventDefault();
    axiosApi
      .post("/requestBoardList/regist/{customer_seq}", requestBoardData)
      .then((res) => {
        navigate("/RequestBoardList");
      })
      .catch((error) => {
        console.error("상담요청게시판 글 등록 에러:", error);
      });
  };

  return (
    <>
      <div>
        <h3>상담 요청 등록</h3>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="requestBoardData_title">제목:</label>
            <input
              type="text"
              id="requestBoardData_title"
              name="requestBoardData_title"
              value={requestBoardData.requestBoardData_title}
              onChange={handleInputChange}
            />
          </div>
          <div>
            <label htmlFor="requestBoardData_content">내용:</label>
            <textarea
              id="requestBoardData_content"
              name="requestBoardData_content"
              value={requestBoardData.requestBoardData_content}
              onChange={handleInputChange}
            />
          </div>
        </form>
      </div>
    </>
  );
}

export default RequestRegist;
