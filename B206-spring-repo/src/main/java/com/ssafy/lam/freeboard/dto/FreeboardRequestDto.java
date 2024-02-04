package com.ssafy.lam.freeboard.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FreeboardRequestDto {
    private Long user_seq;
    private String freeBoard_title;
    private String username;
    private String userId;
    private String freeBoard_content;
    private Long freeBoard_seq;


    @Builder
    public FreeboardRequestDto(Long user_seq, String freeBoard_title, String username, String userId, String freeBoard_content, Long freeBoard_seq) {
        this.user_seq = user_seq;
        this.freeBoard_title = freeBoard_title;
        this.username = username;
        this.userId = userId;
        this.freeBoard_content = freeBoard_content;
        this.freeBoard_seq = freeBoard_seq;
    }
}
