package com.ssafy.lam.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentRequestDto {
    private Long freeboard_seq; // 댓글이 달려있는 게시글 번호
    private Long user_seq; // 댓글을 단 사용자 번호
    private String customer_name; // 댓글을 단 사용자 이름
    private String customerId; // 댓글을 단 사용자 아이디
    private String comment_content; //  댓글 내용
    private Long comment_seq; // 댓글 번호
    private LocalDateTime regdate; // 댓글 작성일


    @Builder
    public CommentRequestDto(Long freeboard_seq, Long user_seq, String customer_name, String customerId, String comment_content, Long comment_seq, LocalDateTime regdate) {
        this.freeboard_seq = freeboard_seq;
        this.user_seq = user_seq;
        this.customer_name = customer_name;
        this.customerId = customerId;
        this.comment_content = comment_content;
        this.comment_seq = comment_seq;
        this.regdate = regdate;
    }
}
