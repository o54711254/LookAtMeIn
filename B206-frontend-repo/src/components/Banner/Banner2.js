import bannerImg from "../../assets/banner2.png";
import styles from "./Banner2.module.css";
function Banner2() {
  return (
    <div className={styles.container}>
      <img src={bannerImg} className={styles.img} />
    </div>
  );
}
export default Banner2;
