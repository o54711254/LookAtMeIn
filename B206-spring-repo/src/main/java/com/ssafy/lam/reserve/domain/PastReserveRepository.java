package com.ssafy.lam.reserve.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PastReserveRepository extends JpaRepository<PastReserve, Long> {
    List<PastReserve> findAllByCustomerUserSeq(Long customerSeq);

}
