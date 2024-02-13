package com.ssafy.lam.hospital.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HospitalDetailDto {

    private long hospitalInfo_seq;
    private String hospitalInfo_name;
    private String hospitalInfo_phoneNumber;
    private String hospitalInfo_introduce;
    private String hospitalInfo_address;
    private String hospitalInfo_email;
    private String hospitalInfo_open;
    private String hospitalInfo_close;
    private String hospitalInfo_url;
    private long userSeq;
    private String profileBase64;
    private String profileType;


//    private double avgScore; // 후기 평균 평점
//    private int cntReviews; // 후기 총 개수
    private double hospitalInfo_avgScore; // 후기 평균 평점
    private int hospitalInfo_cntReviews; // 후기 총 개수
    private boolean hospitalInfo_isLiked;
    // 해당 병원 후기 목록
    // 각 후기의 작성자 피로필 사진, 이름, 후기 제목, 가격, 별점, 카테고리 출력
    // 해당 병원 의사 목록
    // 각 의사의 의사 프로필 사진, 이름, 평균 평점, 후기 개수, 카테고리 출력

    @Builder

    public HospitalDetailDto(long hospitalInfo_seq, String hospitalInfo_name, String hospitalInfo_phoneNumber, String hospitalInfo_introduce, String hospitalInfo_address, String hospitalInfo_email, String hospitalInfo_open, String hospitalInfo_close, String hospitalInfo_url, long userSeq, String profileBase64, String profileType, double hospitalInfo_avgScore, int hospitalInfo_cntReviews, boolean hospitalInfo_isLiked) {
        this.hospitalInfo_seq = hospitalInfo_seq;
        this.hospitalInfo_name = hospitalInfo_name;
        this.hospitalInfo_phoneNumber = hospitalInfo_phoneNumber;
        this.hospitalInfo_introduce = hospitalInfo_introduce;
        this.hospitalInfo_address = hospitalInfo_address;
        this.hospitalInfo_email = hospitalInfo_email;
        this.hospitalInfo_open = hospitalInfo_open;
        this.hospitalInfo_close = hospitalInfo_close;
        this.hospitalInfo_url = hospitalInfo_url;
        this.userSeq = userSeq;
        this.profileBase64 = profileBase64;
        this.profileType = profileType;
        this.hospitalInfo_avgScore = hospitalInfo_avgScore;
        this.hospitalInfo_cntReviews = hospitalInfo_cntReviews;
        this.hospitalInfo_isLiked = hospitalInfo_isLiked;
    }
}
