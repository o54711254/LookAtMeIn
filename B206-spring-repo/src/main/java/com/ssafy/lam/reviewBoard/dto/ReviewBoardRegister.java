
package com.ssafy.lam.reviewBoard.dto;
import lombok.*;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewBoardRegister {
    private long user_seq; // 고객 번호
    private String reviewBoard_title; // 제목
    private String reviewBoard_content; // 내용
    private double reviewBoard_score; // 별점
    private String username; // 작성자 이름
    private String reviewBoard_doctor; // 시술 의사
    private long doctor_seq; // 의사 시퀀스
    private String reviewBoard_region; // 지역
    private String reviewBoard_surgery; // 시술 부위
    private long hospital_seq; // 병원 시퀀스
    private String reviewBoard_hospital; // 병원 이름
    private int reviewBoard_expected_price; // 견적 가격
    private int reviewBoard_surgery_price; // 시술 가격
    private List<String> hashtags;
    @Builder
    public ReviewBoardRegister(long user_seq, String reviewBoard_title, String reviewBoard_content,
                               double reviewBoard_score, String username, String reviewBoard_doctor,
                               long doctor_seq, String reviewBoard_region, String reviewBoard_surgery,
                               long hospital_seq, String reviewBoard_hospital, int reviewBoard_expected_price,
                               int reviewBoard_surgery_price, List<String> hashtags) {
        this.user_seq = user_seq;
        this.reviewBoard_title = reviewBoard_title;
        this.reviewBoard_content = reviewBoard_content;
        this.reviewBoard_score = reviewBoard_score;
        this.username = username;
        this.reviewBoard_doctor = reviewBoard_doctor;
        this.doctor_seq = doctor_seq;
        this.reviewBoard_region = reviewBoard_region;
        this.reviewBoard_surgery = reviewBoard_surgery;
        this.hospital_seq = hospital_seq;
        this.reviewBoard_hospital = reviewBoard_hospital;
        this.reviewBoard_expected_price = reviewBoard_expected_price;
        this.reviewBoard_surgery_price = reviewBoard_surgery_price;
        this.hashtags = hashtags;
    }
}