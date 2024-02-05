package com.ssafy.lam.freeboard.dto;

import com.ssafy.lam.comment.dto.CommentRequestDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FreeboardResponseDto {

    private Long freeboardSeq;
    private String userId;
    private String userEmail;
    private String freeboardTitle;
    private int freeboardCnt;
    private LocalDateTime freeboardRegisterdate;
    private String freeboardContent;
    private List<CommentRequestDto> comments;

    @Builder
    public FreeboardResponseDto(Long freeboardSeq, String userId, String userEmail, String freeboardTitle, int freeboardCnt, LocalDateTime freeboardRegisterdate, String freeboardContent, List<CommentRequestDto> comments) {
        this.freeboardSeq = freeboardSeq;
        this.userId = userId;
        this.userEmail = userEmail;
        this.freeboardTitle = freeboardTitle;
        this.freeboardCnt = freeboardCnt;
        this.freeboardRegisterdate = freeboardRegisterdate;
        this.freeboardContent = freeboardContent;
        this.comments = comments;
    }
}
