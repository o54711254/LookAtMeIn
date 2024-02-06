import React, { useRef, useEffect, useState } from "react";
import * as faceapi from "face-api.js";

function FaceSymmetry() {
  const imageRef = useRef();
  const canvasRef = useRef();
  const [image, setImage] = useState();
  const [asymmetryScore, setAsymmetryScore] = useState(null);

  useEffect(() => {
    const loadModels = async () => {
      const MODEL_URL = "/models";
      await faceapi.nets.ssdMobilenetv1.loadFromUri(MODEL_URL);
      await faceapi.nets.faceLandmark68Net.loadFromUri(MODEL_URL);
    };

    loadModels();
  }, []);

  const handleImageUpload = async (event) => {
    if (event.target.files && event.target.files.length > 0) {
      const file = event.target.files[0];
      const reader = new FileReader();
      reader.onload = (e) => setImage(e.target.result);
      reader.readAsDataURL(file);
      setAsymmetryScore(null);
    }
  };

  const handleImageAnalyze = async () => {
    try {
      const detections = await faceapi
        .detectSingleFace(imageRef.current, new faceapi.SsdMobilenetv1Options())
        .withFaceLandmarks();
      const canvas = canvasRef.current;
      faceapi.matchDimensions(canvas, {
        width: imageRef.current.width,
        height: imageRef.current.height,
      });

      if (detections) {
        const resizedDetections = faceapi.resizeResults(detections, {
          width: imageRef.current.width,
          height: imageRef.current.height,
        });
        faceapi.draw.drawDetections(canvas, resizedDetections);
        faceapi.draw.drawFaceLandmarks(canvas, resizedDetections);

        const score = calculateAsymmetry(resizedDetections.landmarks);
        setAsymmetryScore(score);
      }
    } catch (error) {
      console.error("Face detection failed:", error);
    }
  };

  const calculateAsymmetry = (landmarks) => {
    // 눈의 위치 차이 계산tlqkf
    const leftEye = landmarks.getLeftEye();
    const rightEye = landmarks.getRightEye();
    const eyeAsymmetry = Math.abs(leftEye[0].y - rightEye[0].y);

    // 코의 위치 계산
    const noseBottom = landmarks.getNose()[6];
    const noseAsymmetry = Math.abs(noseBottom.x - imageRef.current.width / 2);

    // 입의 위치 계산
    const mouth = landmarks.getMouth();
    const mouthAsymmetry = Math.abs(mouth[0].x - mouth[6].x);

    // 비대칭 점수 계산
    return eyeAsymmetry + noseAsymmetry + mouthAsymmetry;
  };

  return (
    <div>
      <input type="file" onChange={handleImageUpload} />
      {image && (
        <>
          <img
            ref={imageRef}
            src={image}
            alt="Face"
            onLoad={handleImageAnalyze}
          />
          <canvas ref={canvasRef} />
        </>
      )}
      {asymmetryScore !== null && (
        <p>좌우 비대칭률: {asymmetryScore.toFixed(2) / 2} % 입니다.</p>
      )}
    </div>
  );
}

export default FaceSymmetry;
