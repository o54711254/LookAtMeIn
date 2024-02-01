import {
  combineReducers,
  configureStore,
  getDefaultMiddleware,
} from "@reduxjs/toolkit";
import userReducer from "./user";
import tokenReducer from "./auth";
import requestBoardReducer from "./requestBoard";

// persist-reducer 관련
import storage from "redux-persist/lib/storage";
import { persistReducer } from "redux-persist";

// 여러 개의 reducer를 합치는 combineReducers 함수를 사용하여 root reducer를 생성합니다.
const reducers = combineReducers({
  user: userReducer, // userReducer는 "user" 상태를 처리하는 reducer입니다.
  auth: tokenReducer,
  requestBoard: requestBoardReducer,
});

// Redux Persist를 사용하여 상태 지속성을 설정합니다.
const persistConfig = {
  key: "root", // Redux 상태를 저장할 때 사용되는 키입니다.
  storage, // Redux 상태를 저장하는 데 사용할 스토리지입니다. 여기서는 로컬 스토리지를 사용합니다.
  whitelist: ["user", "hospital", "auth", "requestBoard"], // 지속성할 상태 목록입니다. 여기서는 "user", "hospital", "auth" 상태를 지속성합니다.
};

// persistReducer를 사용하여 지속성이 적용된 root reducer를 생성합니다.
const persistedReducer = persistReducer(persistConfig, reducers);

// configureStore를 사용하여 Redux 스토어를 생성합니다.
const store = configureStore({
  reducer: persistedReducer, // persistReducer로 생성된 reducer를 사용합니다.
  // middleware: getDefaultMiddleware({
  //   serializableCheck: false, // Redux Persist를 사용할 때 serializableCheck를 비활성화합니다.
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        // Redux Persist를 사용할 때 serializableCheck를 비활성화하거나 필요에 따라 구성합니다.
        ignoredActions: [
          "persist/PERSIST",
          "persist/REHYDRATE",
          "persist/PAUSE",
          "persist/PURGE",
          "persist/REGISTER",
        ],
      },
    }),
});
export default store; // 생성된 Redux 스토어를 내보냅니다.
