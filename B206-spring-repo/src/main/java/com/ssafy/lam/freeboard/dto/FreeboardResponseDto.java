package com.ssafy.lam.freeboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FreeboardResponseDto {

    private Long freeboardSeq;
    private String userId;
    private String freeboardTitle;
    private int freeboardCnt;
    private LocalDateTime freeboardRegisterdate;
    private String freeboardContent;

    @Builder
    public FreeboardResponseDto(Long freeboardSeq, String userId, String freeboardTitle, int freeboardCnt, LocalDateTime freeboardRegisterdate, String freeboardContent) {
        this.freeboardSeq = freeboardSeq;
        this.userId = userId;
        this.freeboardTitle = freeboardTitle;
        this.freeboardCnt = freeboardCnt;
        this.freeboardRegisterdate = freeboardRegisterdate;
        this.freeboardContent = freeboardContent;
    }
}
