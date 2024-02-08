package com.ssafy.lam.hospital.dto;

import com.ssafy.lam.hospital.domain.Category;
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
    private List<CategoryDto> doctorCategory; // 의사 전문 카테고리

    @Builder
    public DoctorListDto(Long doctorSeq, String doctorName, double doctorAvgScore, int doctorCntReviews,
                         List<CategoryDto> doctorCategory) {
        this.doctorSeq = doctorSeq;
        this.doctorName = doctorName;
        this.doctorAvgScore = doctorAvgScore;
        this.doctorCntReviews = doctorCntReviews;
        this.doctorCategory = doctorCategory;
    }
}
