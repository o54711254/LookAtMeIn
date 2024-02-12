from sqlalchemy import Column, Integer, String,create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.orm import declarative_base



# SQLALCHEMY_DATABASE_URL = "mysql+pymysql://root:10027760@localhost/lam"
SQLALCHEMY_DATABASE_URL = "mysql+pymysql://ssafy:ssafy@localhost/lam"

engine = create_engine(SQLALCHEMY_DATABASE_URL)
Base = declarative_base()

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

class SurgeryImage(Base):
    __tablename__ = 'upload_file'


    seq = Column(Integer, primary_key=True, name="file_seq")
    type = Column(String(255), name="type")
    name = Column(String(255), name="filename")
    
    

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()  

def save_img(db, name,type):
    
    db_surgeryImg = SurgeryImage(name=name,type=type)
    db.add(db_surgeryImg)
    db.commit()
    db.refresh(db_surgeryImg)
    return db_surgeryImg
    

    

def add_img(before, after, type):
    Base.metadata.create_all(bind=engine)
    with SessionLocal() as db:
        db_beforeImg = save_img(db, before,type)
        db_afterImg = save_img(db, after,type)
        print("db_beforeImg.seq: ", db_beforeImg.seq)
        print("db_afterImg.seq: ", db_afterImg.seq)
 

if __name__ == "__main__":

    add_img("before","after")

