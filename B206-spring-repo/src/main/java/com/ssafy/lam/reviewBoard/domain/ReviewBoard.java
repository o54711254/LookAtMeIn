package com.ssafy.lam.reviewBoard.domain;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
//@AllArgsConstructor
public class ReviewBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_board_seq")
    private long seq;
    @Column(name = "review_board_title")
    private String title; // 제목
    @Column(name = "review_board_content")
    private String content; // 내용
    @Column(name = "review_board_hospital")
    private String hospital; // 병원 이름
    @Column(name = "review_board_hospital_seq")
    private long hospital_seq; // 병원 시퀀스
    @Column(name = "review_board_doctor")
    private String doctor; // 의사 이름
    @Column(name = "review_board_doctor_seq")
    private long doctor_seq; // 의사 시퀀스
    @Column(name = "review_board_surgery")
    private String surgery; // 시술부위
    @Column(name = "review_board_region")
    private String region; // 지역
    @Column(name = "review_board_score")
    private double score; // 별점
    @Column(name = "review_board_expected_price")
    private int expectedPrice; // 견적 가격
    @Column(name = "review_board_surgery_price")
    private int surgeryPrice; // 시술 가격
    @Column(name = "review_board_regdate")
    private long regdate; // 작성시간
    @Column(name = "review_board_complain")
    private boolean complain; // 신고여부
    @Column(name = "review_board_isdeleted")
    private boolean isdeleted; // 삭제여부
    @Column(name = "review_board_cnt")
    private int cnt = 0; // 조회수

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 고객 seq

    @Builder
    public ReviewBoard(long seq, String title, String content, String hospital, long hospital_seq, String doctor,
                       long doctor_seq, String surgery, String region, double score, int expectedPrice, int surgeryPrice,
                       long regdate, boolean complain, boolean isdeleted, int cnt, User user) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.hospital = hospital;
        this.hospital_seq = hospital_seq;
        this.doctor = doctor;
        this.doctor_seq = doctor_seq;
        this.surgery = surgery;
        this.region = region;
        this.score = score;
        this.expectedPrice = expectedPrice;
        this.surgeryPrice = surgeryPrice;
        this.regdate = regdate;
        this.complain = complain;
        this.isdeleted = isdeleted;
        this.cnt = cnt;
        this.user = user;
    }

    public ReviewBoard toEntity(long seq, String title, String content, String hospital, String doctor, String surgery,
                                String region, double score, int expectedPrice, int surgeryPrice, long regdate,
                                boolean complain, boolean isdeleted, int cnt, long hospital_seq, long doctor_seq, User user) {
        return ReviewBoard.builder()
                .seq(seq)
                .title(title)
                .content(content)
                .hospital(hospital)
                .hospital_seq(hospital_seq)
                .doctor(doctor)
                .doctor_seq(doctor_seq)
                .surgery(surgery)
                .region(region)
                .score(score)
                .expectedPrice(expectedPrice)
                .surgeryPrice(surgeryPrice)
                .regdate(regdate)
                .complain(complain)
                .isdeleted(isdeleted)
                .cnt(cnt)
                .user(user)
                .build();

    }

}
