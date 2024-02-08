package com.ssafy.lam.reviewBoard.controller;

import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.DoctorListDto;
import com.ssafy.lam.hospital.dto.HospitalDto;
import com.ssafy.lam.hospital.service.DoctorService;
import com.ssafy.lam.hospital.service.HospitalService;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardRegister;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardUpdate;
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
import java.util.Map;

@RestController
@RequestMapping("/api/reviewBoard")
//@SpringBootTest
public class ReviewBoardController {

    private Logger log = LoggerFactory.getLogger(ReviewBoardController.class);

    @Autowired
    private final ReviewBoardService reviewBoardService;
    @Autowired
    private final HospitalService hospitalService;
    @Autowired
    private final DoctorService doctorService;

    public ReviewBoardController(ReviewBoardService reviewBoardService, HospitalService hospitalService, DoctorService doctorService) {
        log.info("ReviewBoardController init");
        this.hospitalService = hospitalService;
        this.doctorService = doctorService;
        this.reviewBoardService = reviewBoardService;
    }

    @GetMapping("/list")
    @Operation(summary = "삭제(비활성화)된 게시글들을 제외한 모든 후기 정보를 반환한다.")
    public ResponseEntity<List<ReviewListDisplay>> getAllReview() {
        List<ReviewBoard> reviews = reviewBoardService.getAllReviews();
        List<ReviewListDisplay> reviewDisplay = new ArrayList<>();
        for(ReviewBoard r : reviews) {
            reviewDisplay.add(
                    ReviewListDisplay.builder()
                            .reviewBoard_seq(r.getSeq())
                            .customer_name(r.getUser().getName())
                            .reviewBoard_title(r.getTitle())
                            .reviewBoard_cnt(r.getCnt())
                            .reviewBoard_regDate(r.getRegdate())
                            .reviewBoard_score(r.getScore())
                            .reviewBoard_doctor(r.getDoctor().getDocInfoName())
                            .reviewBoard_region(r.getRegion())
                            .reviewBoard_surgery(r.getSurgery())
                            .reviewBoard_hospital(r.getHospital().getUser().getName())
                            .reviewBoard_expected_price(r.getExpectedPrice())
                            .reviewBoard_surgery_price(r.getSurgeryPrice())
                            .build()
            );

        }
        return new ResponseEntity<>(reviewDisplay, HttpStatus.OK);
    }

    @GetMapping("/{seq}")
    @Operation(summary = "후기 게시글 번호에 해당하는 후기 정보를 반환한다. 단, 삭제(비활성화)된 게시글은 표시하지 않는다.")
    public ResponseEntity<ReviewDisplay> getReview(@PathVariable long seq) {
        ReviewBoard review = reviewBoardService.getReview(seq);
        HospitalDto hospitalDto = hospitalService.getHospital(review.getHospital().getHospitalSeq());
        Doctor doctor = doctorService.getDoctor(review.getDoctor().getDocInfoSeq());
        ReviewDisplay detailReview = null;
        if(review!=null) {
            detailReview = ReviewDisplay.builder()
                    .reviewBoard_seq(seq)
                    .reviewBoard_title(review.getTitle())
                    .reviewBoard_content(review.getContent())
                    .reviewBoard_score(review.getScore())
                    .customer_name(review.getUser().getName())
                    .reviewBoard_doctor(review.getDoctor().getDocInfoName())
                    .reviewBoard_region(review.getRegion())
                    .reviewBoard_surgery(review.getSurgery())
                    .reviewBoard_hospital(hospitalDto.getHospitalInfo_name())
                    .reviewBoard_expected_price(review.getExpectedPrice())
                    .reviewBoard_surgery_price(review.getSurgeryPrice())
                    .reviewBoard_cnt(review.getCnt())
                    .hospital_seq(review.getHospital().getHospitalSeq())
                    .doctor_seq(review.getDoctor().getDocInfoSeq())
                    .build();
        }
        return new ResponseEntity<>(detailReview, HttpStatus.OK);
    }

    @PostMapping("/regist")
    @Operation(summary = "새로운 후기 정보를 생성한다.")
    public ResponseEntity<Void> createReview(@RequestBody ReviewBoardRegister reviewBoardRegister) {
        reviewBoardService.createReview(reviewBoardRegister);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    @Operation(summary = "후기 번호에 해당하는 후기 정보를 수정한다.")
    public ResponseEntity<Void> updateReview(@RequestBody ReviewBoardUpdate reviewBoardUpdate) {
        reviewBoardService.updateReview(reviewBoardUpdate);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/delete/{seq}")
    @Operation(summary = "후기 게시글 번호에 해당하는 후기 정보를 삭제(비활성화)한다.")
    public ResponseEntity<Map<String, Boolean>> deactivateReview(@PathVariable long seq) {
        reviewBoardService.deactivateReview(seq);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/report/{seq}")
    @Operation(summary = "신고받은 후기 게시글 번호에 해당하는 후기 정보를 '신고됨'으로 수정한다.")
    public ResponseEntity<Void> reportReview(@PathVariable Long seq) {
        reviewBoardService.reportReview(seq);
        return ResponseEntity.ok().build();
    }

}
