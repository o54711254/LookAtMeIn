package com.ssafy.lam.reviewBoard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.Hospital;
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
<<<<<<< HEAD
    private long seq;
=======
    private Long seq;

>>>>>>> 0ed0587268a376e13877834cf223d62f3d220767
    @Column(name = "review_board_title")
    private String title; // 제목
    @Column(name = "review_board_content")
    private String content; // 내용
//    @Column(name = "review_board_hospital")
//    private String hospital; // 병원 이름
//    @Column(name = "review_board_hospital_seq")
//    private long hospital_seq; // 병원 시퀀스
//    @Column(name = "review_board_doctor")
//    private String doctor; // 의사 이름
//    @Column(name = "review_board_doctor_seq")
//    private long doctor_seq; // 의사 시퀀스
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
<<<<<<< HEAD
    private long regdate; // 작성시간
    @Column(name = "review_board_complain")
    private boolean complain; // 신고여부
    @Column(name = "review_board_isdeleted")
    private boolean isdeleted; // 삭제여부
=======
    private Long regdate; // 작성시간

    @Column(name = "review_board_complain")
    private Boolean complain; // 신고여부

    @Column(name = "review_board_isdeleted")
    private Boolean isdeleted; // 삭제여부

>>>>>>> 0ed0587268a376e13877834cf223d62f3d220767
    @Column(name = "review_board_cnt")
    private Integer cnt = 0; // 조회수

    @ManyToOne(fetch = FetchType.LAZY)
    private Hospital hospital; // 후기 대상 병원

    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor; // 후기 대상 의사

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user; // 후기 작성 고객

    @OneToOne
    @JoinColumn(name = "upload_file_seq")
    private UploadFile uploadFile;


    @Builder
<<<<<<< HEAD
    public ReviewBoard(long seq, String title, String content, String surgery, String region, double score,
                       int expectedPrice, int surgeryPrice, long regdate, boolean complain, boolean isdeleted,
                       int cnt, Hospital hospital, Doctor doctor, User user) {
=======

    public ReviewBoard(Long seq, String title, String content, String hospital, String doctor, String surgery, String region, double score, Integer expectedPrice, Integer surgeryPrice, Long regdate, Boolean complain, Boolean isdeleted, Integer cnt, User user, UploadFile uploadFile) {
>>>>>>> 0ed0587268a376e13877834cf223d62f3d220767
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.surgery = surgery;
        this.region = region;
        this.score = score;
        this.expectedPrice = expectedPrice;
        this.surgeryPrice = surgeryPrice;
        this.regdate = regdate;
        this.complain = complain;
        this.isdeleted = isdeleted;
        this.cnt = cnt;
        this.hospital = hospital;
        this.doctor = doctor;
        this.user = user;
        this.uploadFile = uploadFile;
    }
}
