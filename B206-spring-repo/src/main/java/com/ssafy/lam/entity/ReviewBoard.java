package com.ssafy.lam.entity;

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
    private long seq;
    private String title; // 제목
    private String content; // 내용
    private String hospital; // 병원
    private String doctor; // 의사
    private String surgery; // 시술부위 -> 추후 별도 클래스 생성 예정
    private String region; // 지역
    private double score; // 별점
    private int price; // 가격
    private long regdate; // 작성시간
    private boolean complain; // 신고여부
    private boolean isdeleted; // 삭제여부
//    @JoinColumn(name = "seq")
//    private Customer customer; // 고객 번호 - 고객 클래스 변경 후 업데이트 예정

    @Builder
    public ReviewBoard(long seq, String title, String content, String hospital, String doctor, String surgery,
                       String region, double score, int price, long regdate, boolean complain, boolean isdeleted) {
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
    }

    public ReviewBoard toEntity(long seq, String title, String content, String hospital, String doctor, String surgery,
                                String region, double score, int price, long regdate, boolean complain,
                                boolean isdeleted) {
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
                .build();

    }

}
