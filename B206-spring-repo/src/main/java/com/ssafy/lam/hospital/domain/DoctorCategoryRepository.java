package com.ssafy.lam.hospital.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorCategoryRepository extends JpaRepository<DoctorCategory, Long> {
     List<DoctorCategory> findAllByDoctorDocInfoSeq(Long doctorSeq);
}
