import React, { useRef, useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import styles from "./Canvas.module.css";
import axiosApi from "../../api/axiosApi";
import plus from "../../assets/plus.png";

const Canvas = () => {
  /**
   * 1. mask 모드(o)
   * 2. 전송되는 정보는 mask_points 전송해야함.(o)
   * 3. 사진 업로드 기능(o)
   * 4. clear버튼 누르면 그린거 사라지게 하기
   * 5. 크기 맞추기(o)
   * 6. 오른쪽에 성형 후 사진 보이게 하기 (o)
   * 7. 파일 선택 다시하면 바뀐 사진 보이게 하기 (o)
   */

  const user = useSelector((state) => state.user);

  const canvasRef = useRef(null);
  const [isDrawing, setIsDrawing] = useState(false);
  const [image, setImage] = useState(null); // 업로드한 이미지
  const [maskPoints, setMaskPoints] = useState([]); // 서버에 보낼 좌표를 저장하는 배열
  const [maskLines, setMaskLines] = useState([]); // 브라우저에 그린 그림의 좌표를 저장하는 배열

  const [sketchLines, setSketchLines] = useState([]); // 브라우저에 그린 그림의 좌표를 저장하는 배열
  const [sketchPoints, setSketchPoints] = useState([]); // 서버에 보낼 좌표를 저장하는 배열

  const [penColor, setPenColor] = useState("black");
  const [strokePoints, setStrokePoints] = useState([]);
  const [strokeLines, setStrokeLines] = useState([]); //

  const [mode, setMode] = useState("mask");
  const [file, setFile] = useState(null);
  const [surgeryImg, setSurgeryImg] = useState("");

  const fileInputRef = useRef(null);

  // 사진에 그림 그리는 부분
  useEffect(() => {
    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");
    context.fillStyle = "black";
    context.clearRect(0, 0, canvas.width, canvas.height);
    if (image) {
      context.drawImage(image, 0, 0, canvas.width, canvas.height);
    }
  }, [image]);

  // 이미지 업로드 되면 그림 그려질 수 있도록 활성화하는 부분
  const handleFileChange = (event) => {
    const file = event.target.files[0];
    setFile(event.target.files[0]);
    const reader = new FileReader();
    reader.onload = (event) => {
      const newImage = new Image();
      newImage.src = event.target.result;
      newImage.onload = () => {
        setImage(newImage);
      };
    };
    reader.readAsDataURL(file);
  };
  // 그림 그리기 시작
  const startDrawing = ({ nativeEvent }) => {
    setIsDrawing(true);
    const { offsetX, offsetY } = nativeEvent;

    // setMaskLines([...maskLines, { x: offsetX, y: offsetY }])
    addPoint(offsetX, offsetY, true);
  };
  const draw = ({ nativeEvent }) => {
    if (!isDrawing) return;
    const { offsetX, offsetY } = nativeEvent;
    addPoint(offsetX, offsetY, false);
  };
  const addPoint = (x, y, isStartPoint) => {
    let newPoints;
    let newLines;

    if (!isStartPoint) {
      if (mode === "mask") {
        newLines = [...maskLines, { x, y }];
        setMaskLines(newLines);
        newPoints = [
          ...maskPoints,
          {
            prev: [
              maskLines[maskLines.length - 1].x,
              maskLines[maskLines.length - 1].y,
            ],
            curr: [x, y],
          },
        ];
        setMaskPoints(newPoints);
      } else if (mode === "sketch") {
        newLines = [...sketchLines, { x, y }];
        setSketchLines(newLines);
        newPoints = [
          ...sketchPoints,
          {
            prev: [
              sketchLines[sketchLines.length - 1].x,
              sketchLines[sketchLines.length - 1].y,
            ],
            curr: [x, y],
          },
        ];
        setSketchPoints(newPoints);
      } else if (mode === "color") {
        newLines = [...strokeLines, { x, y }];
        setStrokeLines(newLines);
        newPoints = [
          ...strokePoints,
          {
            prev: [
              strokeLines[strokeLines.length - 1].x,
              strokeLines[strokeLines.length - 1].y,
            ],
            curr: [x, y],
            color: penColor,
          },
        ];
        // console.log("newPoints: ", newPoints)
        setStrokePoints(newPoints);
      }
    } else {
      if (mode === "mask") {
        setMaskLines([...maskLines, { x, y }]);
      } else if (mode === "sketch") {
        setSketchLines([...sketchLines, { x, y }]);
      } else if (mode === "color") {
        setStrokeLines([...strokeLines, { x, y }]);
      }
    }

    drawLine(x, y, isStartPoint);
  };
  const drawLine = (x, y, isStartPoint) => {
    const context = canvasRef.current.getContext("2d");

    if (mode === "mask") context.strokeStyle = "white";
    else if (mode === "sketch") context.strokeStyle = "black";
    else if (mode === "color") context.strokeStyle = penColor;

    context.lineWidth = mode === "mask" ? 10 : 5;
    context.lineCap = "round";

    let lines;
    if (mode === "mask") lines = maskLines;
    else if (mode === "sketch") lines = sketchLines;
    else if (mode === "color") lines = strokeLines;
    if (!isStartPoint && lines.length > 0) {
      context.beginPath();
      context.moveTo(lines[lines.length - 1].x, lines[lines.length - 1].y);
      context.lineTo(x, y);
      context.stroke();
    }
  };
  // 그림 그리기 멈춤
  const stopDrawing = () => {
    setIsDrawing(false);
    // setLines([]);
  };
  // complete 버튼 누르면 서버에 그린 그림의 좌표를 전송
  const complete = async () => {
    // console.log(lines)

    // setMaskLines([])
    /**
     * 프론트에 이미지 업로드하는 방법
     * 1. formData를 만들고
     * 2. formData에 업로드할 이미지를 key,value 설정해서 append
     *    예를 들어 fromData.append("file", file)sms "file"이라는 이름으로 file 객체를 formData에 추가한다
     *    formData에 append할때마다 key-value형식으로 formData에 추가되며 서버쪽에서는 key값으로 value를 찾을 수 있다
     * 3. axios를 사용해서 서버에 전송한다. 이때 header에 'Content-Type': 'multipart/form-data'를 추가해야한다
     */
    const formData = new FormData();
    formData.append("customerId", "userId"); // 고객 아이디
    formData.append("file", file); // 업로드한 이미지, 서버쪽에 "file"이라는 이름으로 file 객체가 전송된다

    const points = {};
    points["mask_points"] = JSON.stringify(maskPoints);
    points["sketch_points"] = JSON.stringify(sketchPoints);
    points["stroke_points"] = JSON.stringify(strokePoints);
    // console.log("strokePoints: ", strokePoints  )
    // console.log("points: ", points)
    formData.append("points", JSON.stringify(points));
    try {
      // const python_server_url =
        // "https://lookatmein.duckdns.org/python/send/points";

      // const python_server_url = "http://localhost:8000/api/send/points"
      // const response = await axios.post(python_server_url, formData, {
      //   headers: {
      //     "Content-Type": "multipart/form-data",
      //   },
      // });

      const response = await axiosApi.post("/python/send/points", formData,{
          headers:{
              'Content-Type': 'multipart/form-data'
          }
      })

      const base64 = response.data.base64;
      const type = response.data.type;
      const img = `data:image/${type};base64,${base64}`;
      setSurgeryImg(img);
    } catch (error) {
      console.error("전송 오류: ", error);
    }
  };
  const save = async () => {
    // console.log(surgeryImg)
    try {
      const formData = new FormData();
      formData.append("before", file);
      const base64 = surgeryImg.split(",")[1];
      const byteCharacters = atob(base64);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);

      // 바이트 배열을 Blob 객체로 변환
      const blob = new Blob([byteArray], { type: "image/png" });

      formData.append("after", blob, "after.png");
      const response = await axiosApi.post(
        `/api/canvas/save/${user.userSeq}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      console.log(response);
    } catch (error) {
      console.error("저장 오류: ", error);
    }
  };

  const handleColorPickerChange = (event) => {
    setPenColor(event.target.value); // 사용자가 선택한 색상으로 펜 색상 상태 업데이트
    setMode("color");
  };

  const handlePlusClick = () => {
    fileInputRef.current.click();
  };

  return (
    <div className={styles.surgeryContainer}>
      <div className={styles.head}>
        <div className={styles.title}>얼굴 성형</div>
        <div className={styles.content}>
          인공지능 얼굴 성형 기능을 통해 원하는 모습을 미리 경험하세요
        </div>
      </div>

      <div className={styles.surgeryBox}>
        <canvas
          ref={canvasRef}
          width={512}
          height={512}
          onMouseDown={startDrawing}
          onMouseMove={draw}
          onMouseUp={stopDrawing}
        />
        {surgeryImg && <img src={surgeryImg} alt="surgeryImg" />}
      </div>

      <div className={styles.buttonContainer}>
        <input
          type="file"
          onChange={handleFileChange}
          accept="image/*"
          style={{ display: "none" }}
          ref={fileInputRef}
        />

        {!image && (
          <div className={styles.plusButton} onClick={handlePlusClick}>
            <img src={plus} alt="Upload" className={styles.plusImg} />
          </div>
        )}

        <input
          type="color"
          value={penColor}
          onChange={handleColorPickerChange}
          className={styles.color}
        />

        <div className={styles.buttonBox}>
          <button onClick={() => setMode("mask")} className={styles.button}>
            지우기
          </button>
          <button onClick={() => setMode("sketch")} className={styles.button}>
            그리기
          </button>
        </div>

        <div className={styles.buttonBox2}>
          <button onClick={complete} className={styles.button2}>
            완료
          </button>
          <button onClick={save} className={styles.button2}>
            저장
          </button>
        </div>
      </div>
    </div>
  );
};
export default Canvas;
