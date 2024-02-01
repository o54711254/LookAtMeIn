package com.ssafy.lam.reviewBoard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewBoardDto {

    private long reviewSeq;
    private String reviewTitle; // 제목
    private String reviewContent; // 내용
    private String reviewHospital; // 병원
    private String reviewDoctor; // 의사
    private String reviewSurgery; // 시술부위 -> 추후 별도 클래스 생성 예정?
    private String reviewRegion; // 지역
    private double reviewScore; // 별점
    private int reviewPrice; // 가격
    private long reviewRegdate; // 작성시간
    private boolean reviewComplain; // 신고여부
    private boolean reviewIsdeleted; // 삭제여부

    private long customerSeq; // 고객 번호 - 고객 클래스 변경 후 업데이트 예정

    @Builder
    public ReviewBoardDto(long reviewSeq, String reviewTitle, String reviewContent, String reviewHospital, String reviewDoctor,
                          String reviewSurgery, String reviewRegion, double reviewScore, int reviewPrice, long reviewRegdate,
                          boolean reviewComplain, boolean reviewIsdeleted, long customerSeq) {
        this.reviewSeq = reviewSeq;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewHospital = reviewHospital;
        this.reviewDoctor = reviewDoctor;
        this.reviewSurgery = reviewSurgery;
        this.reviewRegion = reviewRegion;
        this.reviewScore = reviewScore;
        this.reviewPrice = reviewPrice;
        this.reviewRegdate = reviewRegdate;
        this.reviewComplain = reviewComplain;
        this.reviewIsdeleted = reviewIsdeleted;
        this.customerSeq = customerSeq;
    }
}
