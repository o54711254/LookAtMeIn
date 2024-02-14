package com.ssafy.lam.reviewBoard.domain;


import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.hashtag.domain.ReviewHashtag;
import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ReviewBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_board_seq")
    private Long seq;

    @Column(name = "review_board_title")
    private String title; // 제목

    @Column(name = "review_board_content")
    private String content; // 내용


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

    @Column(name = "review_board_hospital")
    private String hospital; // 후기 대상 병원

    @Column(name = "review_board_doctor")
    private String doctor; // 후기 대상 의사

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 후기 작성 고객

    @OneToOne
    @JoinColumn(name = "upload_file_seq")
    private UploadFile uploadFile;

    @OneToMany(mappedBy = "reviewBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewHashtag> reviewHashtags = new ArrayList<>();

    public void addReviewHashtag(ReviewHashtag reviewHashtag) {
        this.reviewHashtags.add(reviewHashtag);
        reviewHashtag.setReviewBoard(this);
    }

    @Builder
    public ReviewBoard(Long seq, String title, String content, String surgery, String region, double score, int expectedPrice, int surgeryPrice, long regdate, boolean complain, boolean isdeleted, int cnt, String hospital, String doctor, User user, UploadFile uploadFile, List<ReviewHashtag> reviewHashtags) {
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
        this.reviewHashtags = reviewHashtags;
    }
}
