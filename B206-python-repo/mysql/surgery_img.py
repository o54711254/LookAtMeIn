from sqlalchemy import Column, Integer, String,create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.orm import declarative_base



# SQLALCHEMY_DATABASE_URL = "mysql+pymysql://root:10027760@localhost/lam"
SQLALCHEMY_DATABASE_URL = "mysql+pymysql://root:10027760@158.180.88.174/lam"

engine = create_engine(SQLALCHEMY_DATABASE_URL)
Base = declarative_base()

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

class SurgeryImage(Base):
    __tablename__ = 'surgeryImg'

    surgeryImg_seq = Column(Integer, primary_key=True)
    surgeryImg_before = Column(String(255))
    surgeryImg_after = Column(String(255))

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()  

def save_img(db, surgeryImg_before: str, surgeryImg_after):
    db_surgeryImg = SurgeryImage(surgeryImg_before=surgeryImg_before, surgeryImg_after=surgeryImg_after)
    db.add(db_surgeryImg)
    db.commit()
    db.refresh(db_surgeryImg)
    return db_surgeryImg
    

    

def add_img(before, after):
    Base.metadata.create_all(bind=engine)
    with SessionLocal() as db:
        db_surgeryImg = save_img(db, before, after)
        print(db_surgeryImg.surgeryImg_seq)
 

if __name__ == "__main__":

    add_img("before","after")

