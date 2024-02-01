import { useEffect, useState } from "react";
import axiosApi from "../../../api/axiosApi";

function HospitalList() {
  const [postList, setPostList] = useState([]);
  const [listType, setListType] = useState("all");

  useEffect(() => {
    const listURl =
      listType === "all" ? `/admin/hoslist` : `/admin/hoslist/reject`;

    axiosApi
      .get(listURl)
      .then((res) => {
        setPostList(res.data);
      })
      .catch((error) => {
        console.log("병원 목록을 가져오는데 실패했습니다.", error);
      });
  }, [listType]); // listType이 변경될 때마다 useEffect 실행

  return (
    <div>
      <button onClick={() => setListType("all")}>전체 병원 목록</button>
      <button onClick={() => setListType("reject")}>미승인 병원 목록</button>
      {postList.length > 0 ? (
        <div>
          {postList.map((post, index) => {
            <li key={index}>
              <div>{post.hospital_name}</div>
              <div>{post.statusCode}</div>
            </li>;
          })}
        </div>
      ) : (
        <div>병원 내용이 없습니다.</div>
      )}
    </div>
  );
}
export default HospitalList;
