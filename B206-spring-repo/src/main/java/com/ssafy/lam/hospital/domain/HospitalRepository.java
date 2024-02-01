package com.ssafy.lam.hospital.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {
    Optional<Hospital> findByUserUserSeq(Long userSeq);
}
