package com.ssafy.lam.reviewBoard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
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
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviewBoard")
//@SpringBootTest
public class ReviewBoardController {

    private Logger log = LoggerFactory.getLogger(ReviewBoardController.class);

    MultipartConfig multipartConfig = new MultipartConfig();

    // 파일이 업로드될 디렉토리 경로
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

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
            reviewDisplay.add(new ReviewListDisplay(r.getSeq(), r.getUser().getName(), r.getTitle(), r.getCnt(),
                    r.getRegdate(), r.getScore(), r.getDoctor(), r.getRegion(), r.getSurgery(), r.getHospital(),
                    r.getExpectedPrice(), r.getSurgeryPrice()));
        }
        return new ResponseEntity<>(reviewDisplay, HttpStatus.OK);
    }

    @GetMapping("/{seq}")
    @Operation(summary = "후기 게시글 번호에 해당하는 후기 정보를 반환한다. 단, 삭제(비활성화)된 게시글은 표시하지 않는다.")
    public ResponseEntity<ReviewDisplay> getReview(@PathVariable long seq) {
        ReviewBoard review = reviewBoardService.getReview(seq);
        ReviewDisplay detailReview = null;
        if(review!=null) {
            try{
                detailReview = ReviewDisplay.builder()
                        .reviewBoard_seq(review.getSeq())
                        .reviewBoard_title(review.getTitle())
                        .reviewBoard_content(review.getContent())
                        .reviewBoard_score(review.getScore())
                        .customer_name(review.getUser().getName())
                        .reviewBoard_doctor(review.getDoctor())
                        .reviewBoard_region(review.getRegion())
                        .reviewBoard_surgery(review.getSurgery())
                        .reviewBoard_hospital(review.getHospital())
                        .reviewBoard_expected_price(review.getExpectedPrice())
                        .reviewBoard_surgery_price(review.getSurgeryPrice())
                        .reviewBoard_cnt(review.getCnt())
                        .build();
                if(review.getUploadFile() != null){
                    Path path = Paths.get(uploadPath + "/"+review.getUploadFile().getName());
                    String base64 = EncodeFile.encodeFileToBase64(path);
                    String imageType = review.getUploadFile().getType();
                    detailReview.setBase64(base64);
                    detailReview.setImageType(imageType);
                }


            }catch(Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(detailReview, HttpStatus.OK);
    }

    @PostMapping("/regist")
    @Operation(summary = "새로운 후기 정보를 생성한다.")
    public ResponseEntity<Void> createReview(@RequestParam("reviewBoardData") String reviewBoardData,
                                             @RequestParam("uploadfile")MultipartFile file){
        try{
            ReviewBoardRegister reviewBoardRegister = new ObjectMapper().readValue(reviewBoardData, ReviewBoardRegister.class);
            reviewBoardService.createReview(reviewBoardRegister, file);
            return ResponseEntity.ok().build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
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

//    @GetMapping("/avg/{seq}")
//    @Operation(summary = "평균")
//    public double avgScore(@PathVariable long seq) {
//        return reviewBoardService.avgScore(seq);
//    }
//
//    @GetMapping("/cnt/{seq}")
//    @Operation(summary = "개수")
//    public double cntReviews(@PathVariable long seq) {
//        return reviewBoardService.cntReviews(seq);
//    }

}
