import { useEffect, useState } from "react";
import axiosApi from "../../../api/axiosApi";
import styles from "./HospitalList.module.css";

function HospitalList() {
  const [postList, setPostList] = useState([]);
  const [listType, setListType] = useState("all");

  useEffect(() => {
    const listURl =
      listType === "all" ? `/api/admin/approveHos` : `/api/admin/unapproveHos`;

    axiosApi
      .get(listURl)
      .then((res) => {
        setPostList(res.data);
      })
      .catch((error) => {
        console.log("병원 목록을 가져오는데 실패했습니다.", error);
      });
  }, [listType]); // listType이 변경될 때마다 useEffect 실행
  const handleApprove = (userSeq) => {
    axiosApi
      .patch(`/api/admin/approveHos/${userSeq}`)
      .then((res) => {
        console.log("승인 성공");
        setListType("all");
      })
      .catch((error) => {
        console.log("승인 실패", error);
      });
  };
  const handleReject = (userSeq) => {
    axiosApi
      .patch(`/api/admin/disapproveHos/${userSeq}`)
      .then((res) => {
        console.log("반려 성공");
        setListType("all");
      })
      .catch((error) => {
        console.log("반려 실패", error);
      });
  };

  return (
    <div className={styles.hospitalContainer}>
      <div className={styles.hospitalButton}>
        <button className={styles.button} onClick={() => setListType("all")}>
          전체 병원 목록
        </button>
        <button className={styles.button} onClick={() => setListType("reject")}>
          미승인 병원 목록
        </button>
      </div>
      {postList.length > 0 ? (
        <ul>
          {postList.map((post, index) => (
            <li key={index}>
              <div>{post.hospitalInfo_name}</div>
              <div>승인 상태: {post.approved ? "승인됨" : "미승인"}</div>
              {!post.approve && (
                <div>
                  <button onClick={() => handleApprove(post.userSeq)}>
                    승인
                  </button>
                  <button onClick={() => handleReject(post.userSeq)}>
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
