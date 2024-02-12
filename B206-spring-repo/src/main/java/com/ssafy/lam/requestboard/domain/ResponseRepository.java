package com.ssafy.lam.requestboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response,Long> {
    List<Response> findAllByUserUserSeq(Long userSeq);

}
