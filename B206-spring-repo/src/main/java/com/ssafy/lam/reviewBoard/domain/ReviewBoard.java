package com.ssafy.lam.reviewBoard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
//@AllArgsConstructor
public class ReviewBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_board_seq")
    private Long seq;

    @Column(name = "review_board_title")
    private String title; // 제목

    @Column(name = "review_board_content")
    private String content; // 내용

    @Column(name = "review_board_hospital")
    private String hospital; // 병원

    @Column(name = "review_board_doctor")
    private String doctor; // 의사

    @Column(name = "review_board_surgery")
    private String surgery; // 시술부위

    @Column(name = "review_board_region")
    private String region; // 지역

    @Column(name = "review_board_score")
    private double score; // 별점
    @Column(name = "review_board_expected_price")
    private Integer expectedPrice; // 견적 가격
    @Column(name = "review_board_surgery_price")
    private Integer surgeryPrice; // 시술 가격
    @Column(name = "review_board_regdate")
    private Long regdate; // 작성시간

    @Column(name = "review_board_complain")
    private boolean complain; // 신고여부

    @Column(name = "review_board_isdeleted")
    private boolean isdeleted; // 삭제여부

    @Column(name = "review_board_cnt")
    private int cnt = 0; // 조회수

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user; // 고객 seq

    @OneToOne
    @JoinColumn(name = "upload_file_seq")
    private UploadFile uploadFile;



    @Builder

    public ReviewBoard(Long seq, String title, String content, String hospital, String doctor, String surgery, String region, double score, Integer expectedPrice, Integer surgeryPrice, Long regdate, boolean complain, boolean isdeleted, int cnt, User user, UploadFile uploadFile) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.hospital = hospital;
        this.doctor = doctor;
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
        this.uploadFile = uploadFile;
    }
}
