import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Pagination, Autoplay } from "swiper";
import Banner1 from "./Banner/Banner1";
import Banner2 from "./Banner/Banner2";
import styles from "./ImageSlider.module.css";

import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import "swiper/css/autoplay";
function ImageSlider() {
  return (
    <div className={styles.container}>
      <Swiper
        modules={[Navigation, Pagination, Autoplay]} // 이 줄 추가
        className="banner"
        spaceBetween={50}
        slidesPerView={1}
        navigation={true}
        pagination={{ clickable: true }}
        autoplay={{ delay: 2000 }}
      >
        <SwiperSlide>
          <Banner1 />
        </SwiperSlide>
        <SwiperSlide>
          <Banner2 />
        </SwiperSlide>
        <SwiperSlide>Slide 3</SwiperSlide>
        <SwiperSlide>Slide 4</SwiperSlide>
      </Swiper>
    </div>
  );
}
export default ImageSlider;
