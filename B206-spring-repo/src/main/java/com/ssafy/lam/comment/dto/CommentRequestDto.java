package com.ssafy.lam.comment.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentRequestDto {
    private Long user_seq;
    private Long freeboard_seq;
    private String customer_name;
    private String comment_content;
    private Long comment_seq;

    @Builder
    public CommentRequestDto(Long user_seq, Long freeboard_seq, String customer_name, String comment_content, Long comment_seq) {
        this.user_seq = user_seq;
        this.freeboard_seq = freeboard_seq;
        this.customer_name = customer_name;
        this.comment_content = comment_content;
        this.comment_seq = comment_seq;
    }
}
