import styles from "./Banner1.module.css";
function Banner1() {
  return (
    <div className={styles.container}>
      <div className={styles.text}>
        <div className={styles.textTitle}>룩앳미인</div>
        <div className={styles.textContents}>
          룩앳미인에서는 <br />
          <br /> 여러분의 아름다움을 위해 <br />
          <br />
          최선을 다하고 있습니다.
        </div>
      </div>
      <div className={styles.img}></div>
    </div>
  );
}
export default Banner1;
