import axios from "axios";
import { useSelector } from "react-redux";

//토큰이 있으면 header에 포함
export default function SetAuthorizationToken() {
  const token = useSelector((state) => state.authToken);
  if (token.accessToken) {
    axios.defaults.headers.common["Authorization"] = `${token}`;
  } else {
    delete axios.defaults.headers.common["Authorization"];
  }
}