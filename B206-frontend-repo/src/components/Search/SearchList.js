import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosApi from "../../api/axiosApi";
import ReviewBoardComponent from "./ReviewBoard/ReviewBoardSearch";
import FreeBoardComponent from "./FreeBoard/FreeBoardSearch";
import HospitalComponent from "./HospitalBoard/HospitalBoardSearch";
import SearchInput from "./SearchInput";

function SearchList() {
  const { query } = useParams();
  const [results, setResults] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [activeTab, setActiveTab] = useState("reviewBoard");

  useEffect(() => {
    const searchResults = async () => {
      setIsLoading(true);
      try {
        const response = await axiosApi.get(`/search?keyword=${query}&category=${activeTab}`);
        setResults(response.data);
      } catch (error) {
        console.log("검색 중 오류 발생", query, error);
      } finally {
        setIsLoading(false);
      }
    };
    searchResults();
  }, [query, activeTab]);

  const renderComponent = () => {
    switch (activeTab) {
      case "reviewBoard":
        return <ReviewBoardComponent results={results} />;
      case "freeboard":
        return <FreeBoardComponent results={results} />;
      case "hospital":
        return <HospitalComponent results={results} />;
      default:
        return <div>선택된 탭이 없습니다.</div>;
    }
  };

  return (
    <div>
      <div>
        <SearchInput/>
    </div>
      <div>
        <button onClick={() => setActiveTab("reviewBoard")} className={activeTab === "reviewBoard" ? "active" : ""}>후기게시판</button>
        <button onClick={() => setActiveTab("freeboard")} className={activeTab === "freeboard" ? "active" : ""}>자유게시판</button>
        <button onClick={() => setActiveTab("hospital")} className={activeTab === "hospital" ? "active" : ""}>병원게시판</button>
      </div>

      {isLoading ? (
        <div>검색 중...</div>
      ) : (
        renderComponent()
      )}
    </div>
  );
}

export default SearchList;
