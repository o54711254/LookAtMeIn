package com.ssafy.lam.comment.service;

import com.ssafy.lam.comment.domain.Comment;
import com.ssafy.lam.comment.domain.CommentRepository;
import com.ssafy.lam.comment.dto.CommentDto;
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

    public Comment createComment(CommentDto commentDto) {
        String userId = userRepository.findById(commentDto.getUser_seq()).get().getUserId();

        Freeboard freeboard = Freeboard.builder().freeboardSeq(commentDto.getFreeboard_seq()).build();
        Comment comment = Comment.builder()
                .content(commentDto.getComment_content())
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

    public Comment updateComment(CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentDto.getComment_seq()).get();
        comment.setContent(commentDto.getComment_content());
        return commentRepository.save(comment);
    }

    public Comment deleteComment(Long comment_seq) {
        Comment comment = commentRepository.findById(comment_seq).get();
        comment.setDeleted(true);
        return commentRepository.save(comment);
    }


}
