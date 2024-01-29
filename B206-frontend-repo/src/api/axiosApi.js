import axios from "axios";

const axiosApi = axios.create({ baseURL: `http://localhost:80` });

export default axiosApi;
