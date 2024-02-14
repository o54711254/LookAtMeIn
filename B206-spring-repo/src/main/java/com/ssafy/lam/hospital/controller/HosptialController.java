package com.ssafy.lam.hospital.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Void> regist(@RequestParam("hospital") String hospital, @RequestParam(value = "registrationFile", required = false) MultipartFile registrationFile) {
        try{
            HospitalRequestDto hospitalRequestDto = new ObjectMapper().readValue(hospital, HospitalRequestDto.class);

            hospitalService.createHospital(hospitalRequestDto.getHospital(), hospitalRequestDto.getCategoryList(), registrationFile);
            return ResponseEntity.ok().build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

//    @PutMapping


    // 병원에서 의사 정보 추가
    @PostMapping("/{hospital_seq}/doctors/regist")
    @Operation(summary = "병")
    public ResponseEntity<Void> createDoctor(@PathVariable Long hospital_seq, @RequestParam("docto원 마이페이지에서 해당 병원에 해당하는 의사(의사 정보, 카테고리 목록, 경력 목록) 추가rData") String doctorData, @RequestParam("doctorProfile") MultipartFile doctorProfile) {
        try{
            DoctorDto doctorDto = new ObjectMapper().readValue(doctorData, DoctorDto.class);
            hospitalService.createDoctor(hospital_seq, doctorDto, doctorDto.getDoc_info_category(), doctorDto.getDoc_info_career(), doctorProfile);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // 병원에서 의사 목록 조회
    @GetMapping("/{hospital_seq}/doctors")
    @Operation(summary = "병원 마이페이지에서 해당 병원에 해당하는 의사 목록 조회")
    public ResponseEntity<List<Doctor>> getHospitalDoctors(@PathVariable long hospital_seq) {
        List<Doctor> doctors = hospitalService.getHospitalDoctorList(hospital_seq);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @PutMapping("/mypage/modify/{userSeq}")
    @Operation(summary = "병원 정보 수정")
    public ResponseEntity<Void> modify(@PathVariable Long userSeq,
                                       @RequestParam("hospitalData") String hospitalData,
                                       @RequestParam(value = "profile", required = false) MultipartFile profile) {
        try{
            HospitalDto hospitalDto = new ObjectMapper().readValue(hospitalData, HospitalDto.class);

            log.info("수정 정보 : {}", hospitalDto);
            hospitalService.updateHospital(userSeq, hospitalDto, profile);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

}
