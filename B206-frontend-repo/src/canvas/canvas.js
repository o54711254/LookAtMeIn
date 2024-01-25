import React, {useRef, useState, useEffect } from 'react';
import axios from 'axios';
const Canvas = ()=>{

    /**
     * 1. mask 모드(o)
     * 2. 전송되는 정보는 mask_points 전송해야함.(o)
     * 3. 사진 업로드 기능(o)
     * 4. clear버튼 누르면 그린거 사라지게 하기
     * 5. 크기 맞추기(o)
     * 6. 오른쪽에 성형 후 사진 보이게 하기
     */

    const canvasRef = useRef(null);
    const [isDrawing, setIsDrawing] = useState(false);
    const [lines, setLines] = useState([]); // 브라우저에 그린 그림의 좌표를 저장하는 배열
    const [image, setImage] = useState(null); // 업로드한 이미지
    const [sketchPoints, setSketchPoints] = useState([]); // 서버에 보낼 좌표를 저장하는 배열
    const [file, setFile] = useState(null)

    const [surgeryImg, setSurgeryImg] = useState('')    

    useEffect(() => {
        const canvas = canvasRef.current;
        const context = canvas.getContext('2d');
        context.fillStyle = 'black';
        context.clearRect(0, 0, canvas.width, canvas.height);
        if(image){
            context.drawImage(image,0,0,canvas.width,canvas.height);
        }
    }, [image])

    useEffect(() => {
    },[])   

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

    const startDrawing = ({ nativeEvent }) => {
        setIsDrawing(true);
        const { offsetX, offsetY } = nativeEvent;
        
        setLines([...lines, { x: offsetX, y: offsetY }])

    }

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

    const stopDrawing = () => {
        setIsDrawing(false);
        // setLines([]);   
    }

    const complete = async () => {
        // console.log(lines)
        setLines([])


        const formData = new FormData();
        formData.append("customerId", "userId"); // 고객 아이디
        formData.append("file", file) // 업로드한 이미지
  
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
            console.log("url : ", response.data.url)
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