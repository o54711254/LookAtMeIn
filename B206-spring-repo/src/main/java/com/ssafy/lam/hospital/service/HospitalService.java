package com.ssafy.lam.hospital.service;

import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.CategoryDto;
import com.ssafy.lam.hospital.dto.HospitalDto;

import java.util.List;

public interface HospitalService {
    Hospital createHospital(HospitalDto hospitalDto, List<CategoryDto> caregoryDtoList);
}
