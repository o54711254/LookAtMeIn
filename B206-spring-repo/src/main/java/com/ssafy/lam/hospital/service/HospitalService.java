package com.ssafy.lam.hospital.service;

import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.CategoryDto;
import com.ssafy.lam.hospital.dto.HospitalDetailDto;
import com.ssafy.lam.hospital.dto.HospitalDto;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;

import java.util.List;

public interface HospitalService {
    com.ssafy.lam.hospital.domain.Hospital createHospital(HospitalDto hospitalDto, List<CategoryDto> caregoryDtoList);

    HospitalDto getHospital(long userId);

    Hospital updateHospital(long userSeq, HospitalDto hospitalDto);

    //////////

    HospitalDetailDto getHospitalInfo(Long hospitalSeq); // 고객이 병원 페이지 조회
    List<ReviewBoard> getReviewsByHospital(Long hospitalSeq); // 해당 병원에 해당하는 후기 목록 조회
//    List<Doctor> getHospitalDoctorList(Long hospitalSeq); // 병원의 의사 목록 조회
}
