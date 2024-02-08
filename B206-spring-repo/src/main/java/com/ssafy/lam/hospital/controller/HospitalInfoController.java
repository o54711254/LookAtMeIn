package com.ssafy.lam.hospital.controller;

import com.ssafy.lam.hospital.domain.Category;
import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.CategoryDto;
import com.ssafy.lam.hospital.dto.DoctorListDto;
import com.ssafy.lam.hospital.dto.HospitalDetailDto;
import com.ssafy.lam.hospital.dto.HospitalDto;
import com.ssafy.lam.hospital.service.DoctorService;
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
    private final DoctorService doctorService;
    private Logger log = LoggerFactory.getLogger(HosptialController.class);

    @Autowired
    public HospitalInfoController(HospitalService hospitalService, DoctorService doctorService) {
        this.hospitalService = hospitalService;
        this.doctorService = doctorService;
    }

    @GetMapping("/list")
    @Operation(summary = "고객이 병원 전체 목록을 조회한다.")
    public ResponseEntity<List<HospitalDetailDto>> getAllHospital() {
        List<Hospital> hospitalList = hospitalService.getAllHospitalInfo();
        List<HospitalDetailDto> hospitalDetailDtoList = new ArrayList<>();
        for(Hospital h : hospitalList) {
            HospitalDetailDto hospitalDetailDto = hospitalService.getHospitalInfo(h.getUser().getUserSeq());
            hospitalDetailDtoList.add(hospitalDetailDto);
        }
        return new ResponseEntity<>(hospitalDetailDtoList, HttpStatus.OK);
    }

    @GetMapping("/detail/{user_seq}")
    @Operation(summary = "고객이 병원 상세 페이지를 조회한다. - 병원 상세 정보")
    public ResponseEntity<HospitalDetailDto> getHospitalBySeq(@PathVariable Long user_seq) {
        HospitalDetailDto hospitalDetailDto = hospitalService.getHospitalInfo(user_seq);
        return new ResponseEntity<>(hospitalDetailDto, HttpStatus.OK);
    }

    @GetMapping("/reviews/{user_seq}")
    @Operation(summary = "고객이 병원 상세 페이지를 조회한다. - 해당 병원 후기 목록")
    public ResponseEntity<List<ReviewListDisplay>> getHospitalReview(@PathVariable Long user_seq) {
        List<ReviewBoard> reviews = hospitalService.getReviewsByHospital(user_seq);
        List<ReviewListDisplay> reviewDisplay = new ArrayList<>();
        for(ReviewBoard r : reviews) {
            reviewDisplay.add(ReviewListDisplay.builder()
                    .reviewBoard_seq(r.getSeq())
                    .customer_name(r.getUser().getName())
                    .reviewBoard_title(r.getTitle())
                    .reviewBoard_regDate(r.getRegdate())
                    .reviewBoard_score(r.getScore())
                    .reviewBoard_doctor(r.getDoctor().getDocInfoName())
                    .reviewBoard_region(r.getRegion())
                    .reviewBoard_surgery(r.getSurgery())
                    .reviewBoard_hospital(r.getHospital().getUser().getName())
                    .reviewBoard_expected_price(r.getExpectedPrice())
                    .reviewBoard_surgery_price(r.getSurgeryPrice())
                    .build());
        }
        return new ResponseEntity<>(reviewDisplay, HttpStatus.OK);
    }

    @GetMapping("/doctors/{user_seq}")
    @Operation(summary = "고객이 병원 상세 페이지를 조회한다. - 해당 병원 의사 목록")
    public ResponseEntity<List<DoctorListDto>> getHospitalDoctors(@PathVariable Long user_seq) {
        List<Doctor> doctors = hospitalService.getHospitalDoctorList(user_seq);
        List<DoctorListDto> doctorDtoList = new ArrayList<>();
        for(Doctor d : doctors) {
            List<Category> category = doctorService.getCategory(d.getDocInfoSeq());
            List<CategoryDto> categoryDto = new ArrayList<>();
            for(Category c : category) {
                categoryDto.add(new CategoryDto(c.getPart()));
            }
            double avgScore = doctorService.getAvgScore(d.getDocInfoSeq());
            int cntReviews = doctorService.getCntReviews(d.getDocInfoSeq());
            doctorDtoList.add(new DoctorListDto(d.getDocInfoSeq(), d.getDocInfoName(), avgScore, cntReviews, categoryDto));
        }
        return new ResponseEntity<>(doctorDtoList, HttpStatus.OK);
    }

}
