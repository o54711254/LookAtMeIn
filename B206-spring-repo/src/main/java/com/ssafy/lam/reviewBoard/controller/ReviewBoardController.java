package com.ssafy.lam.reviewBoard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardRegister;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardUpdate;
import com.ssafy.lam.reviewBoard.dto.ReviewDisplay;
import com.ssafy.lam.reviewBoard.dto.ReviewListDisplay;
import com.ssafy.lam.reviewBoard.service.ReviewBoardService;
import com.ssafy.lam.user.domain.User;
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
    private CustomerRepository customerRepository;

    @Autowired
    private final ReviewBoardService reviewBoardService;

    public ReviewBoardController(ReviewBoardService reviewBoardService, CustomerRepository customerRepository) {
        log.info("ReviewBoardController init");
        this.reviewBoardService = reviewBoardService;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/list")
    @Operation(summary = "삭제(비활성화)된 게시글들을 제외한 모든 후기 정보를 반환한다.")
    public ResponseEntity<List<ReviewListDisplay>> getAllReview() {
        List<ReviewBoard> reviews = reviewBoardService.getAllReviews();
        List<ReviewListDisplay> reviewDisplay = new ArrayList<>();
        for(ReviewBoard r : reviews) {
            ReviewListDisplay reviewListDisplay = ReviewListDisplay.builder()
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
                    .build();

            User user = r.getUser();
            Customer customer = customerRepository.findByUserUserSeq(user.getUserSeq()).get();
            if(customer.getProfile() != null){
                UploadFile profileFile = customer.getProfile();
                Path path = Paths.get(uploadPath + "/"+profileFile.getName());
                try{
                    String customerProfileBase64 = EncodeFile.encodeFileToBase64(path);
                    String customerProfileType = profileFile.getType();
                    reviewListDisplay.setCustomerProfileBase64(customerProfileBase64);
                    reviewListDisplay.setCustomerProfileType(customerProfileType);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }



            reviewDisplay.add(reviewListDisplay);
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
                        .reviewBoard_doctor(review.getDoctor().getDocInfoName())
                        .reviewBoard_region(review.getRegion())
                        .reviewBoard_surgery(review.getSurgery())
                        .reviewBoard_hospital(review.getHospital().getUser().getName())
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

                User user = review.getUser();
                Customer customer = customerRepository.findByUserUserSeq(user.getUserSeq()).get();
                if(customer.getProfile() != null){
                    UploadFile profileFile = customer.getProfile();
                    Path path = Paths.get(uploadPath + "/"+profileFile.getName());
                    try{
                        String customerProfileBase64 = EncodeFile.encodeFileToBase64(path);
                        String customerProfileType = profileFile.getType();
                        detailReview.setCustomerProfileBase64(customerProfileBase64);
                        detailReview.setCustomerProfileType(customerProfileType);

                    }catch (Exception e) {
                        e.printStackTrace();
                    }
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
    public ResponseEntity<Void> createReview(@RequestParam("reviewBoardData") String reviewBoardData, @RequestParam(value = "uploadFile", required = false) MultipartFile file){
        log.info("reviewBoardData:{}", reviewBoardData);
        log.info("uploadfile:{}" , file);
        try{
            ReviewBoardRegister reviewBoardRegister = new ObjectMapper().readValue(reviewBoardData, ReviewBoardRegister.class);
            reviewBoardService.createReview(reviewBoardRegister, null);
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
