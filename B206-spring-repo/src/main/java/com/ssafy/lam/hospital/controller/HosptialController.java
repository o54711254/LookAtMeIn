package com.ssafy.lam.hospital.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.hospital.domain.*;
import com.ssafy.lam.hospital.dto.*;
import com.ssafy.lam.hospital.service.DoctorService;
import com.ssafy.lam.hospital.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/hospital")
public class HosptialController {
    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private Logger log = LoggerFactory.getLogger(HosptialController.class);
    MultipartConfig multipartConfig = new MultipartConfig();
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

    @Autowired
    public HosptialController(HospitalService hospitalService, DoctorService doctorService) {
        this.hospitalService = hospitalService;
        this.doctorService = doctorService;
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
    @Operation(summary = "병원 마이페이지에서 해당 병원에 해당하는 의사 정보 추가")
    public ResponseEntity<Doctor> createDoctor(@PathVariable Long hospital_seq,
                                               @RequestParam("doctorData") String doctorData,
                                               @RequestParam(value = "doctorProfile", required = false) MultipartFile doctorProfile) {
        try{
            DoctorDto doctorDto = new ObjectMapper().readValue(doctorData, DoctorDto.class);
            Doctor doctor = hospitalService.createDoctor(hospital_seq, doctorDto, doctorProfile);
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/doctor/modify/{doctor_seq}")
    @Operation(summary = "병원 마이페이지에서 해당 병원에 해당하는 의사 정보 수정")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long doctor_seq,
                                               @RequestParam("doctorData") String doctorData,
                                               @RequestParam(value = "doctorProfile", required = false) MultipartFile doctorProfile) {
        try {
            DoctorDto doctorDto = new ObjectMapper().readValue(doctorData, DoctorDto.class);
            log.info("수정 정보: {}", doctorDto);
            Doctor doctor = doctorService.updateDoctor(doctor_seq, doctorDto, doctorProfile);
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

//    @PostMapping("/doctor/delete/{doctor_seq}")
//    @Operation(summary = "병원 마이페이지에서 해당 병원에 해당하는 의사 정보 삭제(연결된 병원 seq 0으로 변경)")
//    public ResponseEntity<Void> deleteDoctor(@PathVariable Long doctor_seq) {
//        try {
//            Doctor doctor = doctorService.getDoctor(doctor_seq);
//            // 의사 삭제
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().build();
//        }
//    }

    // 병원에서 의사 목록 조회
    @GetMapping("/{hospital_seq}/doctors")
    @Operation(summary = "병원 마이페이지에서 해당 병원에 해당하는 의사 목록 조회")
    public ResponseEntity<List<DoctorListDto>> getHospitalDoctors(@PathVariable long hospital_seq) {
        List<Doctor> doctorList = hospitalService.getHospitalDoctorList(hospital_seq);
        List<DoctorListDto> doctorDtoList = new ArrayList<>();
        for(Doctor d : doctorList) {
            Long doctorInfoSeq = d.getDocInfoSeq();

//            List<DoctorCategory> doctorCategoryList = doctorService.getCategory(doctorInfoSeq);
//            List<CategoryDto> categoryDtoList = new ArrayList<>();
//            for(DoctorCategory dc : doctorCategoryList) {
//                categoryDtoList.add(new CategoryDto(dc.getPart()));
//            }

            DoctorListDto doctorDto = DoctorListDto.builder()
                    .doctorSeq(doctorInfoSeq)
                    .doctorName(d.getDocInfoName())
                    .doctorAvgScore(doctorService.getAvgScore(doctorInfoSeq))
                    .doctorCntReviews(doctorService.getCntReviews(doctorInfoSeq))
                    .doctorCategory(d.getDocInfoCategory())
                    .build();

            if(d.getProfile() != null){
                UploadFile doctorProfile = d.getProfile();
                Path path = Paths.get(uploadPath + "/" + doctorProfile.getName());
                try {
                    String doctorProfileBase64 = EncodeFile.encodeFileToBase64(path);
                    String doctorProfileType = doctorProfile.getType();
                    doctorDto.setDoctorProfileBase64(doctorProfileBase64);
                    doctorDto.setDoctorProfileType(doctorProfileType);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            doctorDtoList.add(doctorDto);
        }
        return new ResponseEntity<>(doctorDtoList, HttpStatus.OK);
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
