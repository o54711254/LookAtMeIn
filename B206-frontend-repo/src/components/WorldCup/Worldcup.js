import { useNavigate } from "react-router-dom";
import styles from "./Worldcup.module.css";

function Worldcup() {
  const navigate = useNavigate();

  const goToManWorldcup = () => {
    navigate("man");
  };
  const goToWomanWorldcup = () => {
    navigate("woman");
  };

  return (
    <div className={styles.worldcupContainer}>
      <div className={styles.title}>이상향 월드컵</div>
      <div className={styles.content}>
        이상향 월드컵을 통해 내가 바라는 나의 모습을 확인하세요!
      </div>
      <div className={styles.genderButtons}>
        <button className={styles.button} onClick={goToManWorldcup}>
          남성용
        </button>
        <button className={styles.button} onClick={goToWomanWorldcup}>
          여성용
        </button>
      </div>
    </div>
  );
}
export default Worldcup;
