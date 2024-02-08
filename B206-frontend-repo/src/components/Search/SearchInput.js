import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./SearchInput.module.css";
import searchIcon from "../../assets/search.png";

function SearchInput({ initialQuery = "" }) {
  const [searchTerm, setSearchTerm] = useState(initialQuery);
  const navigate = useNavigate();

  function handleSearch() {
    navigate(`/search/${searchTerm}`);
  }
  function handleKeyDown(e) {
    if (e.key === "Enter") {
      handleSearch();
    }
  }

  return (
    <div className={styles.searchInputContainer}>
      <input
        className={styles.searchInput}
        type="text"
        placeholder="원하는 정보를 검색하세요.."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        onKeyDown={handleKeyDown}
      />
      <img
        src={searchIcon}
        className={styles.searchIcon}
        alt="searchIcon"
        onClick={handleSearch}
      />
    </div>
  );
}
export default SearchInput;
