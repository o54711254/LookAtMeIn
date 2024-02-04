package com.ssafy.lam.freeboard.dto;

import com.ssafy.lam.comment.dto.CommentDto;
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
    private String freeboardTitle;
    private int freeboardCnt;
    private LocalDateTime freeboardRegisterdate;
    private String freeboardContent;
    private List<CommentDto> comments;

    @Builder
    public FreeboardResponseDto(Long freeboardSeq, String userId, String freeboardTitle, int freeboardCnt, LocalDateTime freeboardRegisterdate, String freeboardContent, List<CommentDto> comments) {
        this.freeboardSeq = freeboardSeq;
        this.userId = userId;
        this.freeboardTitle = freeboardTitle;
        this.freeboardCnt = freeboardCnt;
        this.freeboardRegisterdate = freeboardRegisterdate;
        this.freeboardContent = freeboardContent;
        this.comments = comments;
    }
}
