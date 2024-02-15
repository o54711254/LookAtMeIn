package com.ssafy.lam.requestboard.domain;

import com.ssafy.lam.requestboard.dto.ResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RequestboardRepository extends JpaRepository<Requestboard, Long> {
    List<Requestboard> findAllByUserUserSeqAndIsDeletedFalse(Long userSeq);

    List<Requestboard> findByIsDeletedFalse();

    Optional<Requestboard> findBySeqAndIsDeletedFalse(Long seq);

}
