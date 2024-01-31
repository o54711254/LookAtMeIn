import { useEffect, useState } from "react";
import axiosApi from "../../../api/axiosApi";
function Consulting() {
  const [postList, setPostList] = useState([]);
  useEffect(() => {
    axiosApi
      .get(`api/post/list`)
      .then((res) => {
        setPostList(res.data);
      })
      .catch((error) => {
        console.log("상담 내역을 불러오는데 실패했습니다.", error);
      });
  });
  return (
    <div>
      {postList.length > 0 ? (
        <div>
          {postList.map((post, index) => (
            <li key={index}>
              <div>{post.id}</div>
              <div>내용 : {post.contents}</div>
            </li>
          ))}
        </div>
      ) : (
        <div>상담 내역이 없습니다.</div>
      )}
    </div>
  );
}
export default Consulting;
