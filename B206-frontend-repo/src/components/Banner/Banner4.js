import bannerImg from "../../assets/banner4.png";
import styles from "./Banner4.module.css";
function Banner4() {
  return (
    <div className={styles.container}>
      <img src={bannerImg} className={styles.img} />
    </div>
  );
}
export default Banner4;
