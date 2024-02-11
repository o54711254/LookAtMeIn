package com.ssafy.lam.freeboard.domain;

import com.ssafy.lam.comment.dto.CommentRequestDto;
import com.ssafy.lam.freeboard.dto.FreeboardRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FreeboardRepository extends JpaRepository<Freeboard, Long> {
    List<Freeboard> findByIsDeletedFalse();
    Optional<Freeboard> findByfreeboardSeqAndIsDeletedFalse(Long freeBoardSeq);

    List<Freeboard> findByUserUserSeqAndIsDeletedFalse(Long userSeq);

    List<Freeboard> findByComplainTrueAndIsDeletedFalse();

}
