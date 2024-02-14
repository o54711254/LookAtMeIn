package com.ssafy.lam.requestboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response,Long> {
    @Query(value = "select res from Response res where res.requestboard.seq = (select req.seq from Requestboard req where req.user.userSeq = :userSeq)")
    List<Response> findAllByUserUserSeq(Long userSeq);

}
