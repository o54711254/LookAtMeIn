package com.ssafy.lam.requestboard.domain;

import com.ssafy.lam.requestboard.dto.ResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestboardRepository extends JpaRepository<Requestboard, Long> {

    List<Requestboard> findByIsDeletedFalse();

    List<Requestboard> findByUserUserSeqAndIsDeletedFalse(Long userSeq);

    Optional<Requestboard> findBySeqAndIsDeletedFalse(Long seq);


}
