import { useState } from "react";
import { useNavigate } from "react-router-dom";

function SearchInput() {
  const [searchTerm, setSearchTerm] = useState("");
  const navigate = useNavigate();

  function handleSearch() {
    navigate(`/search/${searchTerm}`);
  }

  return (
    <div>
      <input
        type="text"
        placeholder="검색하세요"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      <button onClick={handleSearch}>검색</button>
    </div>
  );
}
export default SearchInput;
