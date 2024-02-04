package com.ssafy.lam.comment;

import com.ssafy.lam.comment.domain.Comment;
import com.ssafy.lam.comment.dto.CommentRequestDto;
import com.ssafy.lam.comment.service.CommentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("댓글 등록 테스트")
    @Transactional
    public void createCommentTest() {
        // given
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .user_seq(1L)
                .freeboard_seq(1L)
                .customer_name("customer")
                .comment_content("content")
                .build();
        // when

        Comment comment = commentService.createComment(commentRequestDto);
        // then
        System.out.println("comment = " + comment);
        Assertions.assertThat(comment.getContent()).isEqualTo("content" );
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    @Transactional
    public void updateCommentTest(){
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .comment_seq(1L)
                .comment_content("update content")
                .build();
        Comment comment = commentService.updateComment(commentRequestDto);
        Assertions.assertThat(comment.getContent()).isEqualTo("update content");
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    @Transactional
    public void deleteCommentTest(){
        Comment comment = commentService.deleteComment(1L);
        Assertions.assertThat(comment.isDeleted()).isEqualTo(true);
    }
}
