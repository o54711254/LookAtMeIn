import { useEffect, useState } from "react";
import axiosApi from "../../../api/axiosApi";

function HospitalList() {
  const [postList, setPostList] = useState([]);
  const [listType, setListType] = useState("all");

  useEffect(() => {
    const listURl =
      listType === "all" ? `/api/admin/approveHost` : `/api/admin/unapproveHos`;

    axiosApi
      .get(listURl)
      .then((res) => {
        setPostList(res.data);
      })
      .catch((error) => {
        console.log("병원 목록을 가져오는데 실패했습니다.", error);
      });
  }, [listType]); // listType이 변경될 때마다 useEffect 실행
  const handleApprove = (hos_seq) => {
    axiosApi
      .patch(`/admin/hos/approve/${hos_seq}`)
      .then((res) => {
        console.log("승인 성공");
        setListType("all");
      })
      .catch((error) => {
        console.log("승인 실패", error);
      });
  };
  const handleReject = (hos_seq) => {
    axiosApi
      .patch(`/admin/hos/approve/${hos_seq}`)
      .then((res) => {
        console.log("승인 성공");
        setListType("all");
      })
      .catch((error) => {
        console.log("승인 실패", error);
      });
  };

  return (
    <div>
      <button onClick={() => setListType("all")}>전체 병원 목록</button>
      <button onClick={() => setListType("reject")}>미승인 병원 목록</button>
      {postList.length > 0 ? (
        <ul>
          {postList.map((post, index) => (
            <li key={index}>
              <div>{post.hospital_name}</div>
              <div>승인 상태: {post.approve ? "승인됨" : "미승인"}</div>
              {!post.approve && (
                <div>
                  <button onClick={() => handleApprove(post.hos_seq)}>
                    승인
                  </button>
                  <button onClick={() => handleReject(post.hos_seq)}>
                    반려
                  </button>
                </div>
              )}
            </li>
          ))}
        </ul>
      ) : (
        <div>병원 내용이 없습니다.</div>
      )}
    </div>
  );
}
export default HospitalList;
