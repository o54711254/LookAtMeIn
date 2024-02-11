package com.ssafy.lam.reviewBoard.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequestDto {
    private Long user_seq;
    private String reviewBoard_title;
    private String reviewBoard_content;
    private String reviewBoard_score;
    private String username;
    private String reviewBoard_doctor;
    private String reviewBoard_region;
    private String reviewBoard_surgery;
    private String reviewBoard_hospital;
    private Integer reviewBoard_expected_price;
    private Integer reviewBoard_surgery_price;
    MultipartFile uploadFile;

    @Builder
    public ReviewRequestDto(Long user_seq, String reviewBoard_title, String reviewBoard_content, String reviewBoard_score, String username, String reviewBoard_doctor, String reviewBoard_region, String reviewBoard_surgery, String reviewBoard_hospital, Integer reviewBoard_expected_price, Integer reviewBoard_surgery_price, MultipartFile uploadFile) {
        this.user_seq = user_seq;
        this.reviewBoard_title = reviewBoard_title;
        this.reviewBoard_content = reviewBoard_content;
        this.reviewBoard_score = reviewBoard_score;
        this.username = username;
        this.reviewBoard_doctor = reviewBoard_doctor;
        this.reviewBoard_region = reviewBoard_region;
        this.reviewBoard_surgery = reviewBoard_surgery;
        this.reviewBoard_hospital = reviewBoard_hospital;
        this.reviewBoard_expected_price = reviewBoard_expected_price;
        this.reviewBoard_surgery_price = reviewBoard_surgery_price;
        this.uploadFile = uploadFile;
    }
}
