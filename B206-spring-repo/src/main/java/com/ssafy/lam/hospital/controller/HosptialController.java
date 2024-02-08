package com.ssafy.lam.hospital.controller;


import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.dto.*;
import com.ssafy.lam.hospital.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital")
public class HosptialController {
    private final HospitalService hospitalService;
    private Logger log = LoggerFactory.getLogger(HosptialController.class);

    @Autowired
    public HosptialController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping("/regist")
    @Operation(summary = "병원 정보를 등록한다.")
    public ResponseEntity<Void> regist(@RequestBody HospitalRequestDto hospitalRequestDto) {
        log.info("regist hospital : {}, Category {}", hospitalRequestDto.getHospital(), hospitalRequestDto.getCategoryList());

        hospitalService.createHospital(hospitalRequestDto.getHospital(), hospitalRequestDto.getCategoryList());
        return ResponseEntity.ok().build();
    }
    
    // 병원에서 의사 정보 추가
    @PostMapping("/{user_seq}/doctors/regist")
    @Operation(summary = "병원 마이페이지에서 해당 병원에 해당하는 의사(의사 정보, 카테고리 목록, 경력 목록) 추가")
    public ResponseEntity<Void> createDoctor(@PathVariable Long user_seq, @RequestBody DoctorDto doctorDto) {
        hospitalService.createDoctor(user_seq, doctorDto, doctorDto.getDoc_info_category(), doctorDto.getDoc_info_career());
        return ResponseEntity.ok().build();
    }
    
    // 병원에서 의사 목록 조회
    @GetMapping("/{user_seq}/doctors")
    @Operation(summary = "병원 마이페이지에서 해당 병원에 해당하는 의사 목록 조회")
    public ResponseEntity<List<Doctor>> getHospitalDoctors(@PathVariable long user_seq) {
        List<Doctor> doctors = hospitalService.getHospitalDoctorList(user_seq);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

}
