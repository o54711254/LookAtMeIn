package com.ssafy.lam.reserve.domain;


import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.coordinator.domain.Coordinator;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    // 고객 또는 병원 사용자 시퀀스로 예약 정보 조회
    @Query("SELECT r FROM Reserve r WHERE r.customer.userSeq = :userSeq OR r.hospital.userSeq = :userSeq")
    List<Reserve> findByUserSeq(Long userSeq);
}
