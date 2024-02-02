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
    private String hospital; // 병원
    @Column(name = "review_board_doctor")
    private String doctor; // 의사
    @Column(name = "review_board_surgery")
    private String surgery; // 시술부위
    @Column(name = "review_board_region")
    private String region; // 지역
    @Column(name = "review_board_score")
    private double score; // 별점
    @Column(name = "review_board_price")
    private int price; // 가격
    @Column(name = "review_board_regdate")
    private long regdate; // 작성시간
    @Column(name = "review_board_complain")
    private boolean complain; // 신고여부
    @Column(name = "review_board_isdeleted")
    private boolean isdeleted; // 삭제여부
    @Column(name = "review_board_cnt")
    private int cnt = 0; // 조회수

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 고객 이름

    @Builder
    public ReviewBoard(long seq, String title, String content, String hospital, String doctor, String surgery,
                       String region, double score, int price, long regdate, boolean complain, boolean isdeleted,
                       int cnt, User user) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.hospital = hospital;
        this.doctor = doctor;
        this.surgery = surgery;
        this.region = region;
        this.score = score;
        this.price = price;
        this.regdate = regdate;
        this.complain = complain;
        this.isdeleted = isdeleted;
        this.cnt = cnt;
        this.user = user;
    }

    public ReviewBoard toEntity(long seq, String title, String content, String hospital, String doctor, String surgery,
                                String region, double score, int price, long regdate, boolean complain,
                                boolean isdeleted, int cnt, User user) {
        return ReviewBoard.builder()
                .seq(seq)
                .title(title)
                .content(content)
                .hospital(hospital)
                .doctor(doctor)
                .surgery(surgery)
                .region(region)
                .score(score)
                .price(price)
                .regdate(regdate)
                .complain(complain)
                .isdeleted(isdeleted)
                .cnt(cnt)
                .user(user)
                .build();

    }

}
