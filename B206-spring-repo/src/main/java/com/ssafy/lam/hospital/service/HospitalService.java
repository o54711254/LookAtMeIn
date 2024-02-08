package com.ssafy.lam.hospital.service;

import com.ssafy.lam.hospital.domain.Career;
import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.*;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;

import java.util.List;

public interface HospitalService {
    Hospital createHospital(HospitalDto hospitalDto, List<CategoryDto> caregoryDtoList);

    HospitalDto getHospital(long userId);

    Hospital updateHospital(long userSeq, HospitalDto hospitalDto);

    //////////

    List<Hospital> getAllHospitalInfo();
    void createDoctor(Long userSeq, DoctorDto doctorDto, List<CategoryDto> categoryDtoList, List<CareerDto> careerDtoList);
    HospitalDetailDto getHospitalInfo(Long userSeq); // 고객이 병원 페이지 조회
    List<ReviewBoard> getReviewsByHospital(Long userSeq); // 해당 병원에 해당하는 후기 목록 조회
    List<Doctor> getHospitalDoctorList(Long userSeq); // 병원의 의사 목록 조회

}
