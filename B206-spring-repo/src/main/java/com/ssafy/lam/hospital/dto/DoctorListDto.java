package com.ssafy.lam.hospital.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DoctorListDto {

    private Long doctorSeq; // 의사 번호
    private String doctorName; // 의사 이름
    private double doctorAvgScore; // 의사 평균 평점
    private int doctorCntReviews; // 의사 후기 개수
    private String doctorCategory; // 의사 전문 카테고리

    private String doctorProfileBase64; // 의사 프로필 이미지
    private String doctorProfileType; // 의사 프로필 이미지 타입

    @Builder
    public DoctorListDto(Long doctorSeq, String doctorName, double doctorAvgScore, int doctorCntReviews,
                         String doctorCategory) {
        this.doctorSeq = doctorSeq;
        this.doctorName = doctorName;
        this.doctorAvgScore = doctorAvgScore;
        this.doctorCntReviews = doctorCntReviews;
        this.doctorCategory = doctorCategory;
    }
}