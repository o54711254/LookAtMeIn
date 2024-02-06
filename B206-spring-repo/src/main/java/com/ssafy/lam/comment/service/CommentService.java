package com.ssafy.lam.comment.service;

import com.ssafy.lam.comment.domain.Comment;
import com.ssafy.lam.comment.domain.CommentRepository;
import com.ssafy.lam.comment.dto.CommentRequestDto;
import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public Comment createComment(CommentRequestDto commentRequestDto) {
        String userId = userRepository.findById(commentRequestDto.getUser_seq()).get().getUserId();

        Freeboard freeboard = Freeboard.builder().freeboardSeq(commentRequestDto.getFreeboard_seq()).build();
        Comment comment = Comment.builder()
                .content(commentRequestDto.getComment_content())
                .userId(userId)
                .freeboard(freeboard)
                .isDeleted(false)
                .regdate(LocalDateTime.now())
                .build();



        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments(Long freeboard_seq) {

        return commentRepository.findByFreeboardFreeboardSeqAndIsDeletedFalse(freeboard_seq);

    }

    public Comment updateComment(CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentRequestDto.getComment_seq()).get();
        comment.setContent(commentRequestDto.getComment_content());
        return commentRepository.save(comment);
    }

    public Comment deleteComment(Long comment_seq) {
        Comment comment = commentRepository.findById(comment_seq).get();
        comment.setDeleted(true);
        return commentRepository.save(comment);
    }


}
