import HospitalInfo from "../components/HospitalBoard/HospitalInfo";
import background from "../assets/background_hos.png";

const HospitalDetail = () => {
  return (
    <>
      <div>
        <div>
          <img src={background} alt="병원상세 배경" width="100%" />
        </div>
        <div>
          <HospitalInfo />
        </div>
      </div>
    </>
  );
};
export default HospitalDetail;
