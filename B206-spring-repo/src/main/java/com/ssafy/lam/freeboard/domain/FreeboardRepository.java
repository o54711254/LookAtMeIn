package com.ssafy.lam.freeboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FreeboardRepository extends JpaRepository<Freeboard, Long> {
    List<Freeboard> findByIsDeletedFalse();
    Optional<Freeboard> findByfreeboardSeqAndIsDeletedFalse(Long freeBoardSeq);
    List<Freeboard> findByUserUserSeqAndIsDeletedFalse(Long userSeq);
}
