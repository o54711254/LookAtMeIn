from fastapi import FastAPI, UploadFile, Request, status, File
from fastapi.responses import JSONResponse, FileResponse
import uvicorn
import os
import json
from config.test_config import TestConfig
from backend import Backend
from model.SingletonModel import SingletonModel


from model.models import create_model
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

    contents = await file.read()
    sketch_points = json.loads(sketch_points)
    uploaded_dir = "./uploaded"
    uploaded_dir = os.path.join(uploaded_dir, customerId) # 한글 처리 안되는 문제 있음
    if os.path.exists(uploaded_dir) is False:
        os.makedirs(uploaded_dir)
    
    # uploadPath = uploaded_dir+"/"+file.filename
    uploadPath = os.path.join(uploaded_dir,file.filename)
    with open(uploadPath, "wb") as f:
        f.write(contents)
    
    singletonModel = SingletonModel.getInstance()
    backend = Backend(singletonModel)
    print("uploadPath: ", uploadPath)
    backend.open(uploadPath)
    backend.complete(sketch_points)


    saveDir = "./save"
    saveDir = os.path.join(saveDir, customerId)
    if os.path.exists(saveDir) is False:
        os.makedirs(saveDir)
    # else:
    #     for name in os.listdir(saveDir):
    #         file_path = os.path.join(saveDir, name)
    #         os.remove(file_path)

    print("savePath: ", os.path.join(saveDir,file.filename))
    backend.save_img(os.path.join(saveDir,file.filename))

    return {
        "url":f"http://localhost:8000/save/{customerId}/{file.filename}"
    }

@app.get("/save/{customerId}/{fileName}")
async def hello(customerId: str, fileName: str):
    return FileResponse(f"./save/{customerId}/{fileName}", media_type="image/png",filename="result.png")

opt = TestConfig().create_config()
model = create_model(opt)
singletonModel = SingletonModel(model)

if __name__ == "__main__":
    # backend = Backend()
    # opt = TestConfig().create_config()
    # model = create_model(opt)
    # singletonModel = SingletonModel(model)
    # print("hello")
    uvicorn.run("main:app", host="localhost", port=8000, reload=True)