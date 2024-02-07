package com.ssafy.lam.reviewBoard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewBoardAdminDto {
    private Long reviewBoard_seq;
    private String reviewBoard_title;
    private String reviewBoard_content;
    private String customer_name;
    private String reviewBoard_doctor;
    private String reviewBoard_region;
    private double score;
    private String reviewBoard_hospital;
    private int reviewBoard_price;
    private long regdate;
    private boolean complain;
    private boolean isdeleted;

    @Builder

    public ReviewBoardAdminDto(Long reviewBoard_seq, String reviewBoard_title, String reviewBoard_content, String customer_name, String reviewBoard_doctor, String reviewBoard_region, double score, String reviewBoard_hospital, int reviewBoard_price, long regdate, boolean complain, boolean isdeleted) {
        this.reviewBoard_seq = reviewBoard_seq;
        this.reviewBoard_title = reviewBoard_title;
        this.reviewBoard_content = reviewBoard_content;
        this.customer_name = customer_name;
        this.reviewBoard_doctor = reviewBoard_doctor;
        this.reviewBoard_region = reviewBoard_region;
        this.score = score;
        this.reviewBoard_hospital = reviewBoard_hospital;
        this.reviewBoard_price = reviewBoard_price;
        this.regdate = regdate;
        this.complain = complain;
        this.isdeleted = isdeleted;
    }
}
