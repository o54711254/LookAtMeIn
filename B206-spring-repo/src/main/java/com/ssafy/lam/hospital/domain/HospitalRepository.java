package com.ssafy.lam.hospital.domain;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByUserUserSeq(Long userSeq);

    List<Hospital> findAllByUserUserSeq(Long userSeq);

    ///////////

    @Query(value = "select r from ReviewBoard r where r.hospital = (select u.name from User u where u.userSeq = (select h.user.userSeq from Hospital h where h.hospitalSeq = :hospitalSeq))")
    List<ReviewBoard> findReviewsByHospitalSeq(@Param("hospitalSeq") Long hospitalSeq);

    @Query(value = "select d from Doctor d where d.hospital.hospitalSeq = :hospitalSeq")
    Optional<List<Doctor>> findDoctorByHospitalSeq(@Param("hospitalSeq") Long hospitalSeq);
}
