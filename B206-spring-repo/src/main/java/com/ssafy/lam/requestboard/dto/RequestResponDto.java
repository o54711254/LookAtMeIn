package com.ssafy.lam.requestboard.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class RequestResponDto {
    private Long seq;
    private String title;
    private String content;
    private Long userSeq;
    private String userName;
    private LocalDate regDate;
    private int requestCnt;
    private int cnt;
    private boolean isDeleted;
    private List<SurgeryDto> surgeries;

    private String customerProfileBase64;
    private String customerProfileType;

    @Builder

    public RequestResponDto(Long seq, String title, String content, Long userSeq, String userName, LocalDate regDate, int requestCnt, int cnt, boolean isDeleted, List<SurgeryDto> surgeries, String customerProfileBase64, String customerProfileType) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.userSeq = userSeq;
        this.userName = userName;
        this.regDate = regDate;
        this.requestCnt = requestCnt;
        this.cnt = cnt;
        this.isDeleted = isDeleted;
        this.surgeries = surgeries;
        this.customerProfileBase64 = customerProfileBase64;
        this.customerProfileType = customerProfileType;
    }
}
