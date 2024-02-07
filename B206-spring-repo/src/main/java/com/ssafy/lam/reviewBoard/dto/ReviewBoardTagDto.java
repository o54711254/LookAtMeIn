package com.ssafy.lam.reviewBoard.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewBoardTagDto {
    private String title;
    private String content;
    private String hospital;
    private String doctor;
    private String surgery;
    private String region;
    private double score;
    private int price;
    private List<String> hashtags;

    @Builder
    public ReviewBoardTagDto(String title, String content, String hospital, String doctor, String surgery, String region, double score, int price, List<String> hashtags) {
        this.title = title;
        this.content = content;
        this.hospital = hospital;
        this.doctor = doctor;
        this.surgery = surgery;
        this.region = region;
        this.score = score;
        this.price = price;
        this.hashtags = hashtags;
    }
}
