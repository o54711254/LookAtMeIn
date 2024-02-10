package com.ssafy.lam.hospital.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalCategoryRepository extends JpaRepository<HospitalCategory, Long> {
    List<HospitalCategory> findAllByHospitalHospitalSeq(Long hospitalSeq);
}
