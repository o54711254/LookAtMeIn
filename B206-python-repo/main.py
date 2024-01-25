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
    # backend.make_sketch(sketch_points)
    backend.complete(sketch_points)


    saveDir = "./save"
    saveDir = os.path.join(saveDir, customerId)
    if os.path.exists(saveDir) is False:
        os.makedirs(saveDir)
    
    # savePath = f"{saveDir}/{file.filename}"
    savePath = os.path.join(saveDir,file.filename)
    print("savePath: ", savePath)
    backend.save_img(savePath)
    
    # return JSONResponse(
    #     status_code=status.HTTP_200_OK,
    #     content={"savePath": f"{savePath}"}
    # )
    return FileResponse(savePath, media_type="image/png",filename="result.png")



if __name__ == "__main__":
    # backend = Backend()
    opt = TestConfig().create_config()
    model = create_model(opt)
    singletonModel = SingletonModel(model)
    
    uvicorn.run(app, host="localhost", port=8000)