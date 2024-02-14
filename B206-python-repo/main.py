from fastapi import FastAPI, UploadFile, Request, status, File
from fastapi.responses import JSONResponse, FileResponse


import uvicorn
import os
import json
from config.test_config import TestConfig
from backend import Backend
from model.SingletonModel import SingletonModel
import uuid
from dotenv import load_dotenv
from model.models import create_model
from sftp import Sftp
import base64
from mysql.surgery_img import add_img

app = FastAPI();

# from sqlalchemy.orm import declarative_base
# Base = declarative_base()


def encodeToBase64(path):
    with open(path, "rb") as image_file:
        image_data = image_file.read()
        base64_data = base64.b64encode(image_data)
        base64_data = base64_data.decode('utf-8')
        return base64_data

def decodeFromBase64(base64Data, path):
    if(base64Data.startswith("data:image")):
        header, base64Data = base64Data.split(',', 1)
    imgdata = base64.b64decode(base64Data)
    
    with open(path, 'wb') as f:
        f.write(imgdata)   

    return path, header 


@app.post("/api/send/points")
async def sketch(file: UploadFile = File(...), points: str=File(...), customerId : str=File(...)):
    before = f"{uuid.uuid4()}.png"

    contents = await file.read()
    points = json.loads(points)
    mask_points = json.loads(points["mask_points"])    
    sketch_pints = json.loads(points["sketch_points"])
    stroke_points = json.loads(points["stroke_points"])
    

    
    
    current_dir = os.path.dirname(os.path.abspath(__file__))
    uploaded_dir = os.path.join(current_dir, "upload")
    
    
    if os.path.exists(uploaded_dir) is False:
        os.makedirs(uploaded_dir)
    else:
        for name in os.listdir(uploaded_dir):
            os.remove(os.path.join(uploaded_dir, name))

    
    
    uploadPath = os.path.join(uploaded_dir, before)
    with open(uploadPath, "wb") as f:
        f.write(contents)
    
  
    singletonModel = SingletonModel.getInstance()
    backend = Backend(singletonModel)

    backend.open(uploadPath)
    backend.complete(mask_points, sketch_pints, stroke_points)

    after = f"{uuid.uuid4()}.png"
    savePath = os.path.join(uploaded_dir, after)

    backend.save_img(savePath)
    encodedImg = encodeToBase64(savePath)
    return{
        "base64": encodedImg,
        "type": "image/png",
        "filename": "result.png"
    }

    
    

@app.on_event("startup")
async def on_startup():

    opt = TestConfig().create_config()
    model = create_model(opt)
    singletonModel = SingletonModel(model)


if __name__ == "__main__":
    
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)