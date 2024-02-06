

import axios from "axios";

const downloadApi = axios.create({
    baseURL: process.env.REACT_APP_URL,
    headers:{
        "Content-Type" : "application/json; charset=UTF-8"
    },
    timeout:5000
})

export default downloadApi;