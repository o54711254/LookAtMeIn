package com.ssafy.lam.hospital.service;

import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.CategoryDto;
import com.ssafy.lam.hospital.dto.HospitalDetailDto;
import com.ssafy.lam.hospital.dto.HospitalDto;

import java.util.List;

public interface HospitalService {
    com.ssafy.lam.hospital.domain.Hospital createHospital(HospitalDto hospitalDto, List<CategoryDto> caregoryDtoList);

    HospitalDto getHospital(long userId);

    Hospital updateHospital(long userSeq, HospitalDto hospitalDto);

    //////////

    HospitalDetailDto getHospitalInfo(long hospitalSeq); // 고객이 병원 페이지 조회
}
