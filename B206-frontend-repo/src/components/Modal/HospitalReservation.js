import React, { useEffect, useState } from "react";
import styles from "./HospitalReservation.module.css";
import DisabledByDefaultOutlinedIcon from "@mui/icons-material/DisabledByDefaultOutlined";
import { useDispatch, useSelector } from "react-redux";
import { modalStateChange } from "../../redux/consulting";
import { useNavigate } from "react-router-dom";

function HospitalReservation() {
  // 삭제 확인 dialog
  const [open, setOpen] = useState(false);
  const handleStateChange = () => setOpen((current) => !current);

  // 임시 data
  const consultingData = useSelector((state) => {
    return {
      // id: state.consulting.reservationId,
      // name: state.consulting.userName,
      // reservationTime: state.consulting.reservationStart,
      // dayOfWeek: state.consulting.dayOfWeek,
      // userSeq: state.consulting.userSeq,
      // hospitalSeq: state.consulting.hospitalSeq,
      // reservationSeq: state.consulting.reservationSeq,
      id: "st",
      name: "st",
      reservationTime: "2024.03.17:14:00",
      dayOfWeek: "월",
      userSeq: 3,
      hospitalSeq: 1,
      reservationSeq: 1,
    };
  });

  // 모달창 닫기 위한 action
  const dispatch = useDispatch();
  const modalClose = () => {
    dispatch(modalStateChange());
  };
  // 상담방 입장 (병원)
  const navigate = useNavigate();
  const enterConsultingRoom = () => {
    modalClose();
    navigate("/meeting", {
      state: {
        userSeq: consultingData.userSeq,
        hospitalSeq: consultingData.hospitalSeq,
        reservationSeq: consultingData.reservationSeq,
        who: "hospital",
      },
    });
  };

  const twolen = (num) => {
    return "0" + num.toString();
  };

  // yyyy-mm-dd
  const dateStr = consultingData.reservationTime.slice(0, 10);
  const year = Number(dateStr.split("-")[0]);
  const month = Number(dateStr.split("-")[1]);
  const day = Number(dateStr.split("-")[2]);
  const hour = Number(consultingData.reservationTime.slice(11, 13)); //9시간 시차 계산
  const minute = consultingData.reservationTime.slice(14, 16);

  // 예약 일정 (30분 전까지만 상담 입장 막아놓기)
  const reservetime = new Date(year, month - 1, day, hour, minute - 30);
  const afterReserveTime = new Date(year, month - 1, day, hour, minute + 30);
  const cancelTime = new Date(year, month - 1, day - 1, hour, minute);

  return (
    <div className={styles.background}>
      <div className={styles.modalbox}>
        <div className={styles.flexcol}>
          <h2 className={styles.confirm}>예약 확인</h2>
          <DisabledByDefaultOutlinedIcon
            fontSize="large"
            onClick={modalClose}
            color="action"
            className={styles.back}
          />
          <p className={`${styles.color} ${styles.hospital}`}>
            {consultingData.name}
          </p>

          <div className={styles.line}></div>
          <div className={`${styles.flexrow} ${styles.mt}`}>
            <p className={`${styles.color} ${styles.mr}`}>예약 일정</p>
            <span>{dateStr}</span>
            <span className={styles.span}>({consultingData.dayOfWeek})</span>
            <span className={styles.span}>{`${
              hour.toString().length < 2 ? twolen(hour) : hour
            }시 ${minute}분`}</span>
          </div>
          <div className={styles.flextop}>
            <p className={`${styles.color} ${styles.mr} ${styles.nomt}`}>
              상담 항목
            </p>
          </div>

          <div className={`${styles.warning} ${styles.mb} ${styles.confirm}`}>
            상담 시 성형 부작용 및 주의사항에 대한 충분한 안내를 요구합니다.
          </div>
          {afterReserveTime > new Date() ? (
            reservetime <= new Date() ? (
              <div className={styles.flexcol}>
                <div className={styles.flex}>
                  <button className={`${styles.okay} ${styles.mr}`}>
                    <a
                      href={
                        "https://apitest.hotsix.duckdns.org/ps-consulting/download/" +
                        consultingData.reservationSeq
                      }
                    >
                      이미지 다운로드
                    </a>
                  </button>
                  <button className={styles.okay} onClick={enterConsultingRoom}>
                    상담 입장
                  </button>
                </div>
              </div>
            ) : cancelTime < new Date() ? (
              <div className={styles.flexcol}>
                <div className={styles.flex}>
                  <button className={`${styles.okay} ${styles.mr}`}>
                    <a
                      href={
                        "https://apitest.hotsix.duckdns.org/ps-consulting/download/" +
                        consultingData.reservationSeq
                      }
                    >
                      이미지 다운로드
                    </a>
                  </button>
                  <button className={styles.yet}>상담 입장</button>
                </div>
                <p className={styles.color}>아직 상담시간이 아닙니다 !</p>
                {/* <p className={styles.color}>상담 1시간 전부터 입장하실 수 있습니다.</p> */}
              </div>
            ) : (
              <div className={styles.flexcol}>
                <div className={styles.flex}>
                  <button
                    className={`${styles.cancel} ${styles.mr}`}
                    onClick={handleStateChange}
                  >
                    상담 취소
                    {open && (
                      <div
                        consultingData={consultingData}
                        modalClose={modalClose}
                      />
                    )}
                  </button>
                  <button className={styles.yet}>상담 입장</button>
                </div>
                <p className={styles.color}>아직 상담시간이 아닙니다 !</p>
              </div>
            )
          ) : (
            <div className={styles.flexcol}>
              <button className={styles.yet}>상담 완료</button>
              <p className={styles.color}>상담이 완료된 예약입니다.</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default HospitalReservation;
