import { useEffect, useState } from "react";
import axiosApi from "../../../api/axiosApi";

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
    <div>
      <div>유저목록</div>
      <div>
        {userList.map((user) => (
          <div>
            <div>userNo : {user.userSeq}</div>
            <div>ID : {user.userId}</div>
            <div>이름 : {user.name}</div>
            <div>회원유형 : {user.roles}</div>
          </div>
        ))}
      </div>
    </div>
  );
}
export default UserList;
