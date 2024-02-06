package com.ssafy.lam.hospital.domain;

import com.ssafy.lam.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByUserUserSeq(Long userSeq);

    List<Hospital> findAllByUserUserSeq(Long userSeq);

    List<Hospital> findByIsApprovedFalse();

    List<Hospital>
}
