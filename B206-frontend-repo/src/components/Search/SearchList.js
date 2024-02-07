import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosApi from "../../api/axiosApi";

function SearchList() {
  const { query } = useParams(); // URL로부터 검색어를 가져옴
  const [results, setResults] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [activeTab, setActiveTab] = useState("reviewBoard"); // 기본적으로 "review" 탭을 활성화

  useEffect(() => {
    const searchResults = async () => {
      setIsLoading(true);
      try {
        // 검색어와 활성 탭에 따라 결과를 가져옴
        const response = await axiosApi.get(`/search?keyword=${query}&category=${activeTab}`);
        setResults(response.data); // API 응답으로부터 받은 데이터로 상태를 업데이트
        console.log(response.data);
      } catch (error) {
        console.log("검색 중 오류 발생", query, error);
      } finally {
        setIsLoading(false); // 로딩 상태 업데이트
      }
    };
    searchResults();
  }, [query, activeTab]); // query 또는 activeTab이 변경될 때마다 검색을 다시 실행

  return (
    <div>
      <div>
        <button onClick={() => setActiveTab("reviewBoard")} className={activeTab === "review" ? "active" : ""}>후기게시판</button>
        <button onClick={() => setActiveTab("freeboard")} className={activeTab === "free" ? "active" : ""}>자유게시판</button>
        <button onClick={() => setActiveTab("hospital")} className={activeTab === "hospital" ? "active" : ""}>병원게시판</button>
      </div>

      {isLoading ? (
        <div>검색 중...</div>
      ) : (
        <div>
          <h2>{query} 검색결과</h2>
          {results.length > 0 ? (
            results.map((result) => <div key={result.id}>{result.hospitalSeq}</div>)
          ) : (
            <div>검색 결과가 없습니다.</div>
          )}
        </div>
      )}
    </div>
  );
}

export default SearchList;
