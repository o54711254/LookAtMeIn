import { useEffect, useState } from "react";
import axiosAPi from "../../../../api/axiosApi";

function ConsultingList() {
  const [postList, setPostList] = useState([]);
  useEffect(() => {
    axiosAPi
      .get(`/api/post/list`)
      .then((res) => {
        setPostList(res.data);
      })
      .catch((error) => {
        console.log("데이터를 가져오는데 실패했습니다.", error);
      });
  });
  return (
    <div>
      {postList.length > 0 ? (
        <ul>
          {postList.map((post, index) => {
            <li key={index}>
              <div>작성자 : {post.id}</div>
              <div>내용 : {post.contents}</div>
            </li>;
          })}
        </ul>
      ) : (
        <div>상담내역이 없습니다.</div>
      )}
    </div>
  );
}
export default ConsultingList;
