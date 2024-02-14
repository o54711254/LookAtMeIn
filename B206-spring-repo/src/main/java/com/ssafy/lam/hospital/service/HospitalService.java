package com.ssafy.lam.hospital.service;

import com.ssafy.lam.hospital.domain.Career;
import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.*;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.dto.ReviewListDisplay;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HospitalService {
    Hospital createHospital(HospitalDto hospitalDto, List<CategoryDto> caregoryDtoList, MultipartFile registrationFile);

    HospitalDto getHospital(long userId);

    Hospital updateHospital(long userSeq, HospitalDto hospitalDto, MultipartFile profile);

    //////////

    List<Hospital> getAllHospitalInfo();
    Doctor createDoctor(Long hospitalSeq, DoctorDto doctorDto, List<CategoryDto> categoryDtoList, List<CareerDto> careerDtoList, MultipartFile doctorProfile);
    HospitalDetailDto getHospitalInfo(Long hospitalSeq); // 고객이 병원 페이지 조회
    HospitalDetailDto getHospitalLikeInfo(Long hospitalSeq, Long userSeq); // 고객이 병원 페이지 조회 + 찜
    List<ReviewListDisplay> getReviewsByHospital(Long hospitalSeq); // 해당 병원에 해당하는 후기 목록 조회
    List<Doctor> getHospitalDoctorList(Long hospitalSeq); // 병원의 의사 목록 조회

}
