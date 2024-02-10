package com.ssafy.lam.freeboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FreeboardAdminDto {
    private Long freeboardSeq;
    private String userId;
    private String userName;
    private String freeboardTitle;
    private String freeboardContent;
    private boolean complain;
    private boolean isDeleted;

    @Builder
    public FreeboardAdminDto(Long freeboardSeq, String userId, String userName, String freeboardTitle, String freeboardContent, boolean complain, boolean isDeleted) {
        this.freeboardSeq = freeboardSeq;
        this.userId = userId;
        this.userName = userName;
        this.freeboardTitle = freeboardTitle;
        this.freeboardContent = freeboardContent;
        this.complain = complain;
        this.isDeleted = isDeleted;
    }
}
