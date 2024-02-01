package com.ssafy.lam.freeboard.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FreeboardDto {
    private String freeBoard_title;
    private String customer_name;
    private Long userSeq;
    private String freeBoard_content;
    private String userId;

    @Builder
    public FreeboardDto(String freeBoard_title, String customer_name, Long userSeq, String freeBoard_content, String userId) {
        this.freeBoard_title = freeBoard_title;
        this.customer_name = customer_name;
        this.userSeq = userSeq;
        this.freeBoard_content = freeBoard_content;
        this.userId = userId;
    }
}
