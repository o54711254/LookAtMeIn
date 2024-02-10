package com.ssafy.lam.requestboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestboardRepository extends JpaRepository<Requestboard, Long> {

    List<Requestboard> findByIsDeletedFalse();

    Optional<Requestboard> findBySeqAndIsDeletedFalse(Long seq);


}
