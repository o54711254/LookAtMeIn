package com.ssafy.lam.reserve.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    // 고객 또는 병원 사용자 시퀀스로 예약 정보 조회
    @Query("SELECT r FROM Reserve r WHERE r.customer.userSeq = :userSeq OR r.hospital.userSeq = :userSeq AND r.completed = false")
    List<Reserve> findAllByUserSeqAndCompletedFalse(Long userSeq);

    Optional<Reserve> findBySeqAndCompletedFalse(Long reserveSeq);


    @Query("SELECT r FROM Reserve r WHERE r.customer.userSeq = :userSeq OR r.hospital.userSeq = :userSeq AND r.completed = true")
    List<Reserve> findAllByUserSeqAndCompletedTrue(Long userSeq);

    Optional<Reserve> findBySeqAndCompletedTrue(Long reserveSeq);
}
