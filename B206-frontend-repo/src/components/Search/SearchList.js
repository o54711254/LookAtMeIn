import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosAPi from "../../api/axiosApi";

function SearchList() {
  const { query } = useParams();
  const [results, setResults] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [activeTab, setActiveTab] = useState("review");

  useEffect(() => {
    const searhResults = async () => {
      setIsLoading(true);
      try {
        console.log("검색어", query);
        const response = await axiosAPi
          .get(`/api/어쩌고/저쩌고`)
          .then((res) => {
            setResults(res.data);
          });
      } catch (error) {
        console.log("검색 중 오류 발생", query, error);
      }
      setIsLoading(false);
    };
    searhResults();
  }, [query, activeTab]);
  return (
    <div>
      <div>
        <button
          onClick={() => setActiveTab("review")}
          className={activeTab === "review" ? "active" : ""}
        >
          후기게시판
        </button>
        <button
          onClick={() => setActiveTab("free")}
          className={activeTab === "free" ? "active" : ""}
        >
          자유게시판
        </button>
        <button
          onClick={() => setActiveTab("hospital")}
          className={activeTab === "hospital" ? "active" : ""}
        >
          병원게시판
        </button>
      </div>

      {isLoading ? (
        <div>검색 중...</div>
      ) : (
        <div>
          <h2>{query} 검색결과</h2>
          {results.length > 0 ? (
            results.map((result) => <div key={result.id}>{result.title}</div>)
          ) : (
            <div>검색 결과가 없습니다.</div>
          )}
        </div>
      )}
    </div>
  );
}
export default SearchList;
