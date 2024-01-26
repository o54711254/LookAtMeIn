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
app = FastAPI();




@app.get("/api/backend/singleton/test1")
def test1():
    
    singleton = SingletonModel.getInstance()
    print(singleton.getModel())
    return {"message": "test1 success"}

@app.get("/api/backend/singleton/test2")
def test2():
    singleton = SingletonModel.getInstance()
    print(singleton.getModel())

    backend = Backend(singleton)
    backend.open("./uploaded/customerId/00001.png")

    return {"message": "test2 success"}


@app.post("/api/send/sketchPoints")
async def sketch(file: UploadFile = File(...), sketch_points: str=File(...), customerId : str=File(...)):
    # 고객 아이디 출력
    # print(customerId)
    # print(sketch_points)
    sftp_host = os.getenv("SFTP_HOST")
    sftp_user = os.getenv("SFTP_USER")
    sftp_key_file = os.getenv("SFTP_KEY_FILE_PATH")
    sftp = Sftp(host=sftp_host, user=sftp_user, key_file=sftp_key_file)
    filename = f"{uuid.uuid4()}.png"

    contents = await file.read()
    sketch_points = json.loads(sketch_points)
    uploaded_dir = "uploaded"
    uploaded_dir = os.path.join(uploaded_dir, customerId) # 한글 처리 안되는 문제 있음
    if os.path.exists(uploaded_dir) is False:
        os.makedirs(uploaded_dir)
    else:
        for name in os.listdir(uploaded_dir):
            os.remove(os.path.join(uploaded_dir, name))
    
    # uploadPath = uploaded_dir+"/"+file.filename
    uploadPath = os.path.join(uploaded_dir, filename)
    with open(uploadPath, "wb") as f:
        f.write(contents)
    
  
    singletonModel = SingletonModel.getInstance()
    backend = Backend(singletonModel)

    backend.open(uploadPath)
    backend.complete(sketch_points)


    saveDir = "./save"
    saveDir = os.path.join(saveDir, customerId)
    if  os.path.exists(saveDir) is False:
        os.makedirs(saveDir)
    else:
        for name in os.listdir(saveDir):
            os.remove(os.path.join(saveDir, name))

  
    print("savePath: ", os.path.join(saveDir,filename))
    backend.save_img(os.path.join(saveDir,filename))

    # print("uploadPath: ", uploadPath)
    stdin, stdout, stderr = sftp.client.exec_command(f"mkdir -p /home/ubuntu/image/origin/{customerId}")
    remotePath = f"/home/ubuntu/image/origin/{customerId}/{filename}"
    # print("rempotePath: ", remotePath)
    sftp.upload(local_path=uploadPath, remote_path=remotePath)



    # MySQL DB에 저장해야함
    # MySQL DB에 사진원본에 원본의 경로, 사진복사본에 성형된 사진의 경로 저장되야함
    # 단 여기서 경로는 MySQL이 돌아가는 컨테이너의 로컬 경로



    # 
    return {
        "url":f"http://localhost:8000/save/{customerId}/{filename}"
    }

@app.get("/save/{customerId}/{fileName}")
async def hello(customerId: str, fileName: str):
    return FileResponse(f"./save/{customerId}/{fileName}", media_type="image/png",filename="result.png")


@app.on_event("startup")
async def on_startup():
    load_dotenv()
    opt = TestConfig().create_config()
    model = create_model(opt)
    singletonModel = SingletonModel(model)



if __name__ == "__main__":


    uvicorn.run("main:app", host="localhost", port=8000, reload=True)