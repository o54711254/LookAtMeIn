package com.ssafy.lam.hospital;

import com.ssafy.lam.hospital.controller.HosptialController;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.CategoryDto;
import com.ssafy.lam.hospital.dto.HospitalDto;
import com.ssafy.lam.hospital.dto.HospitalRequestDto;
import com.ssafy.lam.hospital.service.HospitalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class HospitalServiceTest {
    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HosptialController hosptialController;

    @Test
    @DisplayName("병원 등록 테스트")
    @Transactional
    public void registTest() throws Exception{
        HospitalDto hospitalDto = HospitalDto.builder()
                .hospitalInfo_id("polf3ya")
                .hospitalInfo_password("1234")
                .hospitalInfo_name("kimheesu")
                .hospitalInfo_phoneNumber("010-1234-1234")
                .hospitalInfo_email("wasda@gmail.com")
                .hospitalInfo_address("서울시 강남구")
                .hospitalInfo_open("09:00")
                .hospitalInfo_close("18:00")
                .hospitalInfo_url("www.naver.com")
                .build();
        CategoryDto categoryDto = new CategoryDto("eye");
        CategoryDto categoryDto2 = new CategoryDto("nose");
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(categoryDto);
        categoryDtoList.add(categoryDto2);
//        Hospital hospital = hospitalService.createHospital(hospitalDto, categoryDtoList);
//        System.out.println("hospital.getHospitalSeq() = " + hospital.getHospitalSeq());
    
    }
}
