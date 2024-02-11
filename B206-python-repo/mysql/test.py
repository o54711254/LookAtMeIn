from sqlalchemy import Column, Integer, String,create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base



SQLALCHEMY_DATABASE_URL = "mysql+pymysql://root:10027760@localhost/lam"


engine = create_engine(SQLALCHEMY_DATABASE_URL)

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()

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
    

def add_img_example():
    with SessionLocal() as db:
        db_surgeryImg = save_img(db, "before", "after")
        print(db_surgeryImg.surgeryImg_seq)
        print(db_surgeryImg.surgeryImg_before)
        print(db_surgeryImg.surgeryImg_after)

if __name__ == "__main__":
    Base.metadata.create_all(bind=engine)
    add_img_example()

