package com.ssafy.lam.hospital.controller;

import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.HospitalDetailDto;
import com.ssafy.lam.hospital.dto.HospitalDto;
import com.ssafy.lam.hospital.service.HospitalService;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.dto.ReviewDisplay;
import com.ssafy.lam.reviewBoard.dto.ReviewListDisplay;
import com.ssafy.lam.reviewBoard.service.ReviewBoardService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/hospital-info")
public class HospitalInfoController {

    private final HospitalService hospitalService;
    private Logger log = LoggerFactory.getLogger(HosptialController.class);

    @Autowired
    public HospitalInfoController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/list")
    @Operation(summary = "고객이 병원 전체 목록을 조회한다.")
    public ResponseEntity<List<HospitalDetailDto>> getAllHospital() {
        List<Hospital> hospitalList = hospitalService.getAllHospitalInfo();
        List<HospitalDetailDto> hospitalDetailDtoList = new ArrayList<>();
        for(Hospital h : hospitalList) {
            HospitalDetailDto hospitalDetailDto = hospitalService.getHospitalInfo(h.getHospitalSeq());
            hospitalDetailDtoList.add(hospitalDetailDto);
        }
        return new ResponseEntity<>(hospitalDetailDtoList, HttpStatus.OK);
    }

    @GetMapping("/detail/{hospital_seq}")
    @Operation(summary = "고객이 병원 상세 페이지를 조회한다. - 병원 상세 정보")
    public ResponseEntity<HospitalDetailDto> getHospitalBySeq(@PathVariable Long hospital_seq) {
        HospitalDetailDto hospitalDetailDto = hospitalService.getHospitalInfo(hospital_seq);
        return new ResponseEntity<>(hospitalDetailDto, HttpStatus.OK);
    }

    @GetMapping("/reviews/{hospital_seq}")
    @Operation(summary = "고객이 병원 상세 페이지를 조회한다. - 해당 병원 후기 목록")
    public ResponseEntity<List<ReviewListDisplay>> getHospitalReview(@PathVariable Long hospital_seq) {
        List<ReviewBoard> reviews = hospitalService.getReviewsByHospital(hospital_seq);
        List<ReviewListDisplay> reviewDisplay = new ArrayList<>();
        for(ReviewBoard r : reviews) {
            reviewDisplay.add(new ReviewListDisplay(r.getSeq(), r.getUser().getName(), r.getTitle(), r.getCnt(),
                    r.getRegdate(), r.getScore(), r.getDoctor(), r.getRegion(), r.getSurgery(), r.getHospital(),
                    r.getExpectedPrice(), r.getSurgeryPrice()));
        }
        return new ResponseEntity<>(reviewDisplay, HttpStatus.OK);
    }

    @GetMapping("/doctors/{hospital_seq}")
    @Operation(summary = "고객이 병원 상세 페이지를 조회한다. - 해당 병원 의사 목록")
    public ResponseEntity<List<Doctor>> getHospitalDoctors(@PathVariable Long hospital_seq) {
        List<Doctor> doctors = hospitalService.getHospitalDoctorList(hospital_seq);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

}
