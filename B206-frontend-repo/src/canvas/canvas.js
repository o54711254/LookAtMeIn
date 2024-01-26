import React, {useRef, useState, useEffect } from 'react';
import axios from 'axios';
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
    const [lines, setLines] = useState([]); // 브라우저에 그린 그림의 좌표를 저장하는 배열
    const [image, setImage] = useState(null); // 업로드한 이미지
    const [sketchPoints, setSketchPoints] = useState([]); // 서버에 보낼 좌표를 저장하는 배열
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
        
        setLines([...lines, { x: offsetX, y: offsetY }])

    }

    // 그림 그리기 중
    const draw = ({nativeEvent}) => {   
        if(!isDrawing)
            return;

        const {offsetX, offsetY} = nativeEvent;
        const newLines = [...lines, {x:offsetX, y:offsetY}];
        setLines(newLines)

        const newSkectchPoints = [...sketchPoints, {
            "prev" : [lines[lines.length - 1].x, lines[lines.length - 1].y],
            "curr" : [offsetX, offsetY] 
        }]

        setSketchPoints(newSkectchPoints)


        const context = canvasRef.current.getContext('2d');
        context.beginPath();
        context.lineWidth = 10;
        context.strokeStyle = 'white';
        context.lineCap = 'round';
        context.moveTo(lines[lines.length - 1].x, lines[lines.length - 1].y);
        context.lineTo(offsetX, offsetY);
        context.stroke();
    }


    // 그림 그리기 멈춤
    const stopDrawing = () => {
        setIsDrawing(false);
        // setLines([]);   
    }


    // complete 버튼 누르면 서버에 그린 그림의 좌표를 전송
    const complete = async () => {
        // console.log(lines)
        setLines([])

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
  
        const points = JSON.stringify(sketchPoints)
        formData.append("sketch_points", points) // 그린 그림의 좌표

        try{
            const python_server_url = "http://localhost:8000/api/send/sketchPoints"

            const response = await axios.post(python_server_url, formData,{
                headers:{
                    'Content-Type': 'multipart/form-data'
                }
            })
            
            // console.log("서버 응답: ", response.data)
            // console.log("hello")
            // console.log("url : ", response.data.url)
            setSurgeryImg(response.data.url)
            

        }catch(error){
            console.error("전송 오류: ", error)
        }
    }


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
             <button onClick={complete}>complete</button>
             {surgeryImg && <img src={surgeryImg} alt="surgeryImg" />}
             
        <div>
           <input type="file" onChange={handleFileChange} accept="image/*" />

          
        </div>
        </div>
    );
}

export default Canvas;