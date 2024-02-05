package com.ssafy.lam.reviewBoard.controller;

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

    public ReviewBoardController(ReviewBoardService reviewBoardService) {
        log.info("ReviewBoardController init");
        this.reviewBoardService = reviewBoardService;
    }

    @GetMapping("/list")
    @Operation(summary = "삭제(비활성화)된 게시글들을 제외한 모든 후기 정보를 반환한다.")
    public ResponseEntity<List<ReviewListDisplay>> getAllReview() {
        List<ReviewBoard> reviews = reviewBoardService.getAllReviews();
        List<ReviewListDisplay> reviewDisplay = new ArrayList<>();
        for(ReviewBoard r : reviews) {


            reviewDisplay.add(new ReviewListDisplay(r.getUser().getName(), r.getTitle(), r.getCnt(), r.getRegdate(),
                    r.getScore(), r.getDoctor(), r.getRegion(), r.getSurgery(), r.getHospital(), r.getPrice()));
        }
        return new ResponseEntity<>(reviewDisplay, HttpStatus.OK);
    }

    @GetMapping("/{seq}")
    @Operation(summary = "후기 게시글 번호에 해당하는 후기 정보를 반환한다. 단, 삭제(비활성화)된 게시글은 표시하지 않는다.")
    public ResponseEntity<ReviewDisplay> getReview(@PathVariable long seq) {
        ReviewBoard review = reviewBoardService.getReview(seq);
        ReviewDisplay detailReview = null;
        if(review!=null) {
            detailReview = new ReviewDisplay(review.getTitle(), review.getContent(), review.getScore(),
                    review.getUser().getName(), review.getDoctor(), review.getRegion(), review.getSurgery(),
                    review.getHospital(), review.getPrice());
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

}
