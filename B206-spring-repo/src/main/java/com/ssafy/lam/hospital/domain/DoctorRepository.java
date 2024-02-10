package com.ssafy.lam.hospital.domain;

import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query(value = "select r from ReviewBoard r where r.doctor = (select d.docInfoName from Doctor d where d.docInfoSeq=:docInfoSeq)")
    List<ReviewBoard> findReviewsByDoctorSeq(Long docInfoSeq);

    @Query(value = "select avg(r.score) from ReviewBoard r where r.doctor = (select d.docInfoName from Doctor d where d.docInfoSeq=:docInfoSeq)")
    Optional<Double> findAvgByDoctorSeq(Long docInfoSeq);

    @Query(value = "select count(r.score) from ReviewBoard r where r.doctor = (select d.docInfoName from Doctor d where d.docInfoSeq=:docInfoSeq)")
    int countByDoctorSeq(Long docInfoSeq);
}
