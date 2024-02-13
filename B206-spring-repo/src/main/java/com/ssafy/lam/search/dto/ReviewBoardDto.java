package com.ssafy.lam.search.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewBoardDto {

    private Long reviewBoard_seq; // 리뷰 게시판 번호
    private String reviewBoard_title; // 제목
    private String reviewBoard_content; // 내용
    private double reviewBoard_score; // 별점
    private String customer_name; // 작성자
    private String reviewBoard_doctor; // 시술 의사
    private String reviewBoard_region; // 지역
    private String reviewBoard_surgery; // 시술 부위
    private String reviewBoard_hospital; // 병원 이름
    private int reviewBoard_expected_price; // 견적 가격
    private int reviewBoard_surgery_price; // 시술 가격
    private int reviewBoard_cnt; // 조회수

    private String hospitalProfileBase64;
    private String hospitalProfileType;

    private String uploadImgBase64;
    private String uploadImgType;

    @Builder
    public ReviewBoardDto(long reviewBoard_seq, String reviewBoard_title, String reviewBoard_content,
                         double reviewBoard_score, String customer_name, String reviewBoard_doctor,
                         String reviewBoard_region, String reviewBoard_surgery, String reviewBoard_hospital,
                         int reviewBoard_expected_price, int reviewBoard_surgery_price, int reviewBoard_cnt) {
        this.reviewBoard_seq = reviewBoard_seq;
        this.reviewBoard_title = reviewBoard_title;
        this.reviewBoard_content = reviewBoard_content;
        this.reviewBoard_score = reviewBoard_score;
        this.customer_name = customer_name;
        this.reviewBoard_doctor = reviewBoard_doctor;
        this.reviewBoard_region = reviewBoard_region;
        this.reviewBoard_surgery = reviewBoard_surgery;
        this.reviewBoard_hospital = reviewBoard_hospital;
        this.reviewBoard_cnt = reviewBoard_cnt;
    }
}
