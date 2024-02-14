package com.ssafy.lam.search.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HospitalDto {

    private long hospitalInfo_seq;
    private String hospitalInfo_name;
    private String hospitalInfo_phoneNumber;
    private String hospitalInfo_introduce;
    private String hospitalInfo_address;
    private String hospitalInfo_open;
    private String hospitalInfo_close;
    private long userSeq;
    private double hospitalInfo_avgScore; // 후기 평균 평점
    private int hospitalInfo_cntReviews; // 후기 총 개수
    // 해당 병원 후기 목록
    // 각 후기의 작성자 피로필 사진, 이름, 후기 제목, 가격, 별점, 카테고리 출력
    // 해당 병원 의사 목록
    // 각 의사의 의사 프로필 사진, 이름, 평균 평점, 후기 개수, 카테고리 출력


    private String hospitalProfileBase64;
    private String hospitalProfileType;

    @Builder
    public HospitalDto(long hospitalInfo_seq, String hospitalInfo_name, String hospitalInfo_phoneNumber,
                       String hospitalInfo_introduce, String hospitalInfo_address, String hospitalInfo_open,
                       String hospitalInfo_close, long userSeq,
                       double hospitalInfo_avgScore, int hospitalInfo_cntReviews) {
        this.hospitalInfo_seq = hospitalInfo_seq;
        this.hospitalInfo_name = hospitalInfo_name;
        this.hospitalInfo_phoneNumber = hospitalInfo_phoneNumber;
        this.hospitalInfo_introduce = hospitalInfo_introduce;
        this.hospitalInfo_address = hospitalInfo_address;
        this.hospitalInfo_open = hospitalInfo_open;
        this.hospitalInfo_close = hospitalInfo_close;
        this.userSeq = userSeq;
        this.hospitalInfo_avgScore = hospitalInfo_avgScore;
        this.hospitalInfo_cntReviews = hospitalInfo_cntReviews;
    }
}
