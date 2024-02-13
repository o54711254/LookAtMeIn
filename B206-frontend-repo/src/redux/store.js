import {
  combineReducers,
  configureStore,
  getDefaultMiddleware,
} from "@reduxjs/toolkit";
import userReducer from "./user";
import tokenReducer from "./auth";
import requestBoardReducer from "./requestBoard";
import hospitalReducer from "./hospital"
import consultingReducer from "./consulting"

// persist-reducer 및 persistStore 관련
import storage from "redux-persist/lib/storage";
import { persistReducer, persistStore } from "redux-persist";

const reducers = combineReducers({
  user: userReducer,
  auth: tokenReducer,
  requestBoard: requestBoardReducer,
  hospital: hospitalReducer,
  consulting: consultingReducer
});

const persistConfig = {
  key: "root",
  storage,
  whitelist: ["user", "auth", "requestBoard", "hospital"],
};

const persistedReducer = persistReducer(persistConfig, reducers);

const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
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

// persistStore 함수를 사용하여 persistor 객체를 생성합니다.
const persistor = persistStore(store);

// store와 persistor를 내보냅니다.
export { store, persistor };
