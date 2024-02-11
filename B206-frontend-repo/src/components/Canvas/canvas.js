import React, {useRef, useState, useEffect } from 'react';
import axios from 'axios';
import axiosApi from '../../api/axiosApi';
const Canvas = ()=>{
    /**
     * 1. mask 모드(o)
     * 2. 전송되는 정보는 mask_points 전송해야함.(o)
     * 3. 사진 업로드 기능(o)
     * 4. clear버튼 누르면 그린거 사라지게 하기
     * 5. 크기 맞추기(o)
     * 6. 오른쪽에 성형 후 사진 보이게 하기 (o)
     * 7. 파일 선택 다시하면 바뀐 사진 보이게 하기 (o)
     */
    const canvasRef = useRef(null);
    const [isDrawing, setIsDrawing] = useState(false);   
    const [image, setImage] = useState(null); // 업로드한 이미지
    const [maskPoints, setMaskPoints] = useState([]); // 서버에 보낼 좌표를 저장하는 배열
    const [maskLines, setMaskLines] = useState([]); // 브라우저에 그린 그림의 좌표를 저장하는 배열
    
    const [sketchLines, setSketchLines] = useState([]); // 브라우저에 그린 그림의 좌표를 저장하는 배열
    const [sketchPoints, setSketchPoints] = useState([]); // 서버에 보낼 좌표를 저장하는 배열
    const [mode, setMode] = useState("mask")
    const [file, setFile] = useState(null)
    const [surgeryImg, setSurgeryImg] = useState('')    
    // 사진에 그림 그리는 부분
    useEffect(() => {
        const canvas = canvasRef.current;
        const context = canvas.getContext('2d');
        context.fillStyle = 'black';
        context.clearRect(0, 0, canvas.width, canvas.height);
        if(image){
            context.drawImage(image,0,0,canvas.width,canvas.height);
        }
    }, [image])
    
    
    // 이미지 업로드 되면 그림 그려질 수 있도록 활성화하는 부분
    const handleFileChange = (event) => {
        const file = event.target.files[0];
        setFile(event.target.files[0])
        const reader = new FileReader();
        reader.onload = (event) => {
            const newImage = new Image();
            newImage.src = event.target.result;
            newImage.onload = () => {
                setImage(newImage);
            }
        }
        reader.readAsDataURL(file);
    }
    // 그림 그리기 시작
    const startDrawing = ({ nativeEvent }) => {
        setIsDrawing(true);
        const { offsetX, offsetY } = nativeEvent;
        
        // setMaskLines([...maskLines, { x: offsetX, y: offsetY }])
        addPoint(offsetX, offsetY, true);
    }
    const draw = ({nativeEvent}) => {
        if(!isDrawing)
            return;
        const {offsetX, offsetY} = nativeEvent;
        addPoint(offsetX, offsetY, false);
    }
    const addPoint = (x, y, isStartPoint) => {
        let newPoints;
        let newLines;
    
        if(!isStartPoint){
            if (mode === 'mask') {
                newLines = [...maskLines, { x, y }];
                setMaskLines(newLines);
                newPoints = [...maskPoints, {
                    "prev":[maskLines[maskLines.length-1].x, maskLines[maskLines.length-1].y],
                    "curr":[x, y]
                }]
                setMaskPoints(newPoints);
            } else if (mode === 'sketch') {
                newLines = [...sketchLines, { x, y }];
                setSketchLines(newLines);
                newPoints = [...sketchPoints, {
                    "prev":[sketchLines[sketchLines.length-1].x, sketchLines[sketchLines.length-1].y],
                    "curr":[x, y]
                }]
                setSketchPoints(newPoints);
            }    
        }else{
            if(mode === 'mask'){
                setMaskLines([...maskLines, { x, y }]);
            }else if(mode === 'sketch'){
                setSketchLines([...sketchLines, { x, y }]);
            }
        }
        
    
        drawLine(x, y, isStartPoint);
    };
    const drawLine = (x, y, isStartPoint) => {
        const context = canvasRef.current.getContext('2d');
        context.strokeStyle = mode === 'mask' ? 'white' : 'black';
        context.lineWidth = mode === 'mask' ? 10 : 5;
        context.lineCap = 'round';
    
        const lines = mode === 'mask' ? maskLines : sketchLines;
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
    }
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
        formData.append("file", file) // 업로드한 이미지, 서버쪽에 "file"이라는 이름으로 file 객체가 전송된다
  
        // const points = JSON.stringify(maskPoints)
        // formData.append("sketch_points", points) // 그린 그림의 좌표
        const points = {}
        points["mask_points"] = JSON.stringify(maskPoints)
        points["sketch_points"] = JSON.stringify(sketchPoints)
        formData.append("points", JSON.stringify(points))
        try{
            const python_server_url = "http://118.42.90.100:8000/api/send/points"
            const response = await axios.post(python_server_url, formData,{
                headers:{
                    'Content-Type': 'multipart/form-data'
                }
            })
            
            const base64 = response.data.base64
            const type = response.data.type
            const img = `data:image/${type};base64,${base64}`
            setSurgeryImg(img)
        }catch(error){
            console.error("전송 오류: ", error)
        }
    }
    const save= async ()=>{
        // console.log(surgeryImg)
        try{
            const formData = new FormData();
            formData.append("after", file)
            const base64 = surgeryImg.split(',')[1] 

            const byteCharacters = atob(base64);
            const byteNumbers = new Array(byteCharacters.length);
            for (let i = 0; i < byteCharacters.length; i++) {
                byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
            const byteArray = new Uint8Array(byteNumbers);
            
            // 바이트 배열을 Blob 객체로 변환
            const blob = new Blob([byteArray], {type: 'image/png'});
            
            formData.append("before", blob ,"after.png")

            const response = await axiosApi.post("/api/canvas/save", formData,{
                headers:{
                    'Content-Type': 'multipart/form-data'
                }
            
            })

                      
            
            console.log(response)
        }catch(error){
            console.error("저장 오류: ", error)
        }
    }
    const clearCanvas = () => {
        const canvas = canvasRef.current;
        const context = canvas.getContext('2d');
        // 캔버스의 전체 영역을 지우는 대신, 그림을 그리는 영역만 지웁니다.
        context.clearRect(0, 0, canvas.width, canvas.height);
    
        // 그린 선들의 데이터를 초기화합니다.
        setMaskPoints([]);
        setSketchPoints([]);
        setMaskLines([]);
        setSketchLines([]);
    
        // 업로드한 이미지가 있다면 캔버스에 다시 그립니다.
        if (image) {
            const img = new Image();
            img.onload = () => {
                context.drawImage(img, 0, 0, canvas.width, canvas.height);
            };
            img.src = image;
        }
    };
    return (
        <div style={{backgroundColor :'black'}}>
            <canvas
                ref={canvasRef}
                width={512}
                height={512}
                onMouseDown={startDrawing}
                onMouseUp={stopDrawing}
                onMouseMove={draw}
            />    
            <button onClick={()=>setMode("mask")}>mask</button>
            <button onClick={()=>setMode("sketch")}>sketch</button>
            <button onClick={clearCanvas}>clear</button>
             <button onClick={complete}>complete</button>
             {surgeryImg && <img src={surgeryImg} alt="surgeryImg" />}
             {/* 저장 버튼은 나중에 지워야함. 상담 화면에서 종료 버튼 누르면 이미지가 DB에 저장 */}
             <button onClick={save}>저장</button> 
            <div>
                <input type="file" onChange={handleFileChange} accept="image/*" />
            </div>
        </div>
          
        
    );
}
export default Canvas;
