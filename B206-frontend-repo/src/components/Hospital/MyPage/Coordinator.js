import { useEffect, useState } from "react";
import axiosApi from "../../../api/axiosApi";
import { useSelector } from "react-redux";

function Coordinator() {
  const [postList, setPostList] = useState([]);
  const user = useSelector((state) => state.user);
  useEffect(() => {
    axiosApi
      .get(`/api/post/list/${user}`)
      .then((res) => {
        setPostList(res.data);
      })
      .catch((error) => {
        console.log("코디네이터 정보를 가져오는데 실패했습니다.", error);
      });
  });
  return (
    <div>
      {postList.length > 0 ? (
        <ul>
          {postList.map((post, index) => {
            <li key={index}>
              <div>{post}</div>
            </li>;
          })}
        </ul>
      ) : (
        <div>의사정보가 없습니다.</div>
      )}
    </div>
  );
}
export default Coordinator;
