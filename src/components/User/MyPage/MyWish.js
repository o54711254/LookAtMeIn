import { useEffect, useState } from "react";
import axiosAPi from "../../../api/axiosApi";

function MyWish() {
  const [wishList, setWishList] = useState([]);
  useEffect(() => {
    axiosAPi
      .get(`/api/wish/list`)
      .then((res) => {
        setWishList(res.data);
      })
      .catch((error) => {
        console.log("데이터를 가져오는데 실패했습니다.", error);
      });
  }, []);
  return (
    <div>
      {wishList.length > 0 ? (
        <ul>
          {wishList.map((wish, index) => (
            <li key={index}>
              인덱스: {index}, 제목: {wish.title}, 작성자: {wish.author}
            </li>
          ))}
        </ul>
      ) : (
        <p>찜목록이 비어있습니다.</p>
      )}
    </div>
  );
}
export default MyWish;
