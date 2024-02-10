package com.ssafy.lam.reviewBoard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewBoardUpdate {

    private long reviewBoard_seq; // 리뷰 게시글 시퀀스
    private String reviewBoard_title; // 제목
    private String reviewBoard_content; // 내용
    private double reviewBoard_score; // 별점
    private String reviewBoard_doctor; // 시술 의사
    private String reviewBoard_region; // 지역
    private String reviewBoard_surgery; // 시술 부위
    private String reviewBoard_hospital; // 병원 이름
    private int reviewBoard_expected_price; // 견적 가격
    private int reviewBoard_surgery_price; // 시술 가격
    private long hospital_seq; // 병원 시퀀스
    private long doctor_seq; // 의사 시퀀스

    @Builder
    public ReviewBoardUpdate(long reviewBoard_seq, String reviewBoard_title, String reviewBoard_content,
                             double reviewBoard_score, String reviewBoard_doctor, String reviewBoard_region,
                             String reviewBoard_surgery, String reviewBoard_hospital, int reviewBoard_expected_price,
                             int reviewBoard_surgery_price, long hospital_seq, long doctor_seq) {
        this.reviewBoard_seq = reviewBoard_seq;
        this.reviewBoard_title = reviewBoard_title;
        this.reviewBoard_content = reviewBoard_content;
        this.reviewBoard_score = reviewBoard_score;
        this.reviewBoard_doctor = reviewBoard_doctor;
        this.reviewBoard_region = reviewBoard_region;
        this.reviewBoard_surgery = reviewBoard_surgery;
        this.reviewBoard_hospital = reviewBoard_hospital;
        this.reviewBoard_expected_price = reviewBoard_expected_price;
        this.reviewBoard_surgery_price = reviewBoard_surgery_price;
        this.hospital_seq = hospital_seq;
        this.doctor_seq = doctor_seq;
    }
}
