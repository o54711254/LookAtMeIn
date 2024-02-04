package com.ssafy.lam.comment.controller;

import com.ssafy.lam.comment.domain.Comment;
import com.ssafy.lam.comment.dto.CommentRequestDto;
import com.ssafy.lam.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    private Logger log = LoggerFactory.getLogger(CommentController.class);


    @PostMapping("/regist")
    @Operation(summary = "댓글 등록", description = "댓글을 등록합니다.")
    @Parameter(name = "commentRequestDto", description = "comment_seq와 comment_content만 사용", required = true)
    public ResponseEntity<Comment> registComment(@RequestBody CommentRequestDto commentRequestDto) {
        log.info("댓글 등록");
        try{
            Comment comment = commentService.createComment(commentRequestDto);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/update")
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @Parameter(name = "commentRequestDto", description = "freeboard_seq, user_seq, customer_name, comment_content만 사용", required = true)
    public ResponseEntity<Comment> updateComment(@RequestBody CommentRequestDto commentRequestDto) {
        log.info("댓글 수정");
        try{
            Comment comment = commentService.updateComment(commentRequestDto);
//        return ResponseEntity.ok(comment);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    @PutMapping("/delete/{comment_seq}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @Parameter(name = "comment_seq", description = "삭제할 댓글의 comment_seq", required = true)
    public ResponseEntity<Comment> deleteComment(@PathVariable Long comment_seq) {
        log.info("삭제하려는 댓글 번호: {}", comment_seq);
        try{
            Comment comment = commentService.deleteComment(comment_seq);
            return ResponseEntity.ok().build() ;
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
