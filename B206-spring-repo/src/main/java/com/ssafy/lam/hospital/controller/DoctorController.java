package com.ssafy.lam.hospital.controller;

import com.ssafy.lam.hospital.domain.Career;
import com.ssafy.lam.hospital.domain.DoctorCategory;
import com.ssafy.lam.hospital.domain.HospitalCategory;
import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.dto.CareerDto;
import com.ssafy.lam.hospital.dto.CategoryDto;
import com.ssafy.lam.hospital.dto.DoctorDto;
import com.ssafy.lam.hospital.service.DoctorService;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.dto.ReviewListDisplay;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{doctor_seq}")
    @Operation(summary = "의사 정보를 조회한다.")
    public ResponseEntity<DoctorDto> getDoctor(@PathVariable Long doctor_seq) {
        List<DoctorCategory> doctorCategories = doctorService.getCategory(doctor_seq);
        List<CategoryDto> categoryDto = new ArrayList<>();
        for(DoctorCategory c : doctorCategories) {
            categoryDto.add(new CategoryDto(c.getPart()));
        }

        List<Career> career = doctorService.getCareer(doctor_seq);
        List<CareerDto> careerDto = new ArrayList<>();
        for(Career c : career) {
            careerDto.add(new CareerDto(c.getCareerStart(), c.getCareerEnd(), c.getCareerContent()));
        }

        Doctor doctor = doctorService.getDoctor(doctor_seq);
        DoctorDto doctorDto = DoctorDto.builder()
                .doc_info_seq(doctor_seq)
                .doc_info_name(doctor.getDocInfoName())
                .doc_info_category(categoryDto)
                .doc_info_career(careerDto)
                .doc_info_avgScore(doctorService.getAvgScore(doctor_seq))
                .doc_info_cntReviews(doctorService.getCntReviews(doctor_seq))
                .build();
        return new ResponseEntity<>(doctorDto, HttpStatus.OK);
    }

    @GetMapping("/reviews/{doctor_seq}")
    @Operation(summary = "고객이 해당 의사의 후기 목록을 조회")
    public ResponseEntity<List<ReviewListDisplay>> getDoctorReview(@PathVariable Long doctor_seq) {
        List<ReviewBoard> reviews = doctorService.getReviewsByDoctor(doctor_seq);
        List<ReviewListDisplay> reviewDisplay = new ArrayList<>();
        for(ReviewBoard r : reviews) {
            reviewDisplay.add(new ReviewListDisplay(r.getSeq(), r.getUser().getName(), r.getTitle(), r.getCnt(),
                    r.getRegdate(), r.getScore(), r.getDoctor().getDocInfoName(), r.getRegion(), r.getSurgery(), r.getHospital().getUser().getName(),
                    r.getExpectedPrice(), r.getSurgeryPrice()));
        }
        return new ResponseEntity<>(reviewDisplay, HttpStatus.OK);
    }

}
