package com.ssafy.lam.reviewBoard.controller;

import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardDto;
import com.ssafy.lam.reviewBoard.service.ReviewBoardService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviewBoard")
//@SpringBootTest
public class ReviewBoardController {

    private Logger log = LoggerFactory.getLogger(ReviewBoardController.class);

    @Autowired
    private final ReviewBoardService reviewBoardService;

    public ReviewBoardController(ReviewBoardService reviewBoardService) {
        log.info("ReviewBoardController init");
        this.reviewBoardService = reviewBoardService;
    }

    @GetMapping("/list")
    @Operation(summary = "모든 후기 정보를 반환한다.")
    public ResponseEntity<List<ReviewBoard>> getAllReview() {
        List<ReviewBoard> reviews = reviewBoardService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{seq}")
    @Operation(summary = "후기 게시글 번호에 해당하는 후기 정보를 반환한다.")
    public ResponseEntity<ReviewBoard> getReview(@PathVariable long seq) {
        ReviewBoard review = reviewBoardService.getReview(seq);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

//    @GetMapping("/search/{type}/{keyword}")
//    @Operation(summary = "후기 게시글 번호에 해당하는 후기 정보를 반환한다.")
//    public ResponseEntity<List<ReviewBoard>> searchReview(@PathVariable String type, @PathVariable String keyword) {
//        List<ReviewBoard> reviewList = reviewBoardService.searchReview(type, keyword);
//        return new ResponseEntity<>(reviewList, HttpStatus.OK);
//    }

    @GetMapping("/hospital/{hospital}")
    @Operation(summary = "후기 게시글에 해당하는 후기 정보를 반환한다.")
    public ResponseEntity<List<ReviewBoard>> findByHospital(@PathVariable String hospital) {
        List<ReviewBoard> reviewList = reviewBoardService.getReviewByHospital(hospital);
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("/doctor/{doctor}")
    @Operation(summary = "후기 게시글에 해당하는 후기 정보를 반환한다.")
    public ResponseEntity<List<ReviewBoard>> findByDoctor(@PathVariable String doctor) {
        List<ReviewBoard> reviewList = reviewBoardService.getReviewByDoctor(doctor);
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("/surgery/{surgery}")
    @Operation(summary = "후기 게시글에 해당하는 후기 정보를 반환한다.")
    public ResponseEntity<List<ReviewBoard>> findBySurgery(@PathVariable String surgery) {
        List<ReviewBoard> reviewList = reviewBoardService.getReviewBySurgery(surgery);
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("/region/{region}")
    @Operation(summary = "후기 게시글에 해당하는 후기 정보를 반환한다.")
    public ResponseEntity<List<ReviewBoard>> findByRegion(@PathVariable String region) {
        List<ReviewBoard> reviewList = reviewBoardService.getReviewByRegion(region);
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("/customerSeq/{customerSeq}")
    @Operation(summary = "후기 게시글 작성자 번호에 해당하는 후기 정보를 반환한다.")
    public ResponseEntity<List<ReviewBoard>> findByCustomerSeq(@PathVariable long customerSeq) {
        List<ReviewBoard> reviewList = reviewBoardService.getReviewByCustomerSeq(customerSeq);
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @PostMapping("/regist")
    @Operation(summary = "새로운 후기 정보를 생성한다.")
    public ResponseEntity<Void> createReview(@RequestBody ReviewBoardDto reviewBoardDto) {
        ReviewBoard review = reviewBoardService.createReview(reviewBoardDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{seq}")
    @Operation(summary = "후기 번호에 해당하는 후기 정보를 수정한다.")
    public ResponseEntity<ReviewBoard> updateReview(@PathVariable long seq, @RequestBody ReviewBoardDto reviewBoardDto) {
        ReviewBoard newReview = reviewBoardService.updateReview(seq, reviewBoardDto);
        return new ResponseEntity<>(newReview, HttpStatus.OK);
    }

    @PutMapping("/delete/{seq}")
    @Operation(summary = "후기 게시글 번호에 해당하는 후기 정보를 삭제(비활성화)한다.")
    public ResponseEntity<Map<String, Boolean>> deactivateReview(@PathVariable long seq) {
        reviewBoardService.deactivateReview(seq);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
