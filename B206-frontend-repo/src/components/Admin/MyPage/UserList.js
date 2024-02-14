import { useEffect, useState } from "react";
import axiosApi from "../../../api/axiosApi";
import styles from "./UserList.module.css";

function UserList() {
  const [userList, setUserList] = useState([]);
  useEffect(() => {
    axiosApi
      .get(`api/user/all`)
      .then((res) => {
        console.log(res.data);
        setUserList(res.data);
      })
      .catch((error) => {
        console.log("유저정보를 가오는데 에러가 발생했습니다.", error);
      });
  }, []);
  return (
    <div className={styles.userListContainer}>
      <div className={styles.head}>유저목록</div>
      <div className={styles.userItemContainer}>
        {userList.map((user) => (
          <div className={styles.userItem}>
            <div className={styles.items}>userNo : {user.userSeq}</div>
            <div className={styles.items}>ID : {user.userId}</div>
            <div className={styles.items}>이름 : {user.name}</div>
            <div className={styles.items}>회원유형 : {user.roles}</div>
          </div>
        ))}
      </div>
    </div>
  );
}
export default UserList;
