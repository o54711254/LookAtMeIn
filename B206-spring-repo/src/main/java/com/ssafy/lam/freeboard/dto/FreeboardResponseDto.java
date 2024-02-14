package com.ssafy.lam.freeboard.dto;

import com.ssafy.lam.comment.dto.CommentRequestDto;
import com.ssafy.lam.file.dto.FileResponseDto;
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
    private Long fileSeq;
    private String base64;
    private String type;

    private String customerProfileBase64;
    private String customerProfileType;

    @Builder
    public FreeboardResponseDto(Long freeboardSeq, String userId, String userEmail, String freeboardTitle, int freeboardCnt, LocalDateTime freeboardRegisterdate, String freeboardContent, List<CommentRequestDto> comments, Long fileSeq, String base64, String type, String customerProfileBase64, String customerProfileType) {
        this.freeboardSeq = freeboardSeq;
        this.userId = userId;
        this.userEmail = userEmail;
        this.freeboardTitle = freeboardTitle;
        this.freeboardCnt = freeboardCnt;
        this.freeboardRegisterdate = freeboardRegisterdate;
        this.freeboardContent = freeboardContent;
        this.comments = comments;
        this.fileSeq = fileSeq;
        this.base64 = base64;
        this.type = type;
        this.customerProfileBase64 = customerProfileBase64;
        this.customerProfileType = customerProfileType;
    }
}
