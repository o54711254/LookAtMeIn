package com.ssafy.lam.search.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class FreeboardDto {

    private Long freeboardSeq;
    private String userId;
    private String userEmail;
    private String freeboardTitle;
    private int freeboardCnt;
    private LocalDateTime freeboardRegisterdate;
    private String freeboardContent;

    private String uploadImgBase64;
    private String uploadImgType;

    private String customerProfileBase64;
    private String customerProfileType;

    @Builder
    public FreeboardDto(Long freeboardSeq, String userId, String userEmail, String freeboardTitle, int freeboardCnt, LocalDateTime freeboardRegisterdate, String freeboardContent) {
        this.freeboardSeq = freeboardSeq;
        this.userId = userId;
        this.userEmail = userEmail;
        this.freeboardTitle = freeboardTitle;
        this.freeboardCnt = freeboardCnt;
        this.freeboardRegisterdate = freeboardRegisterdate;
        this.freeboardContent = freeboardContent;
    }
}
