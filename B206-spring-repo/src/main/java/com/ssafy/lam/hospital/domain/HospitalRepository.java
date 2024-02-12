package com.ssafy.lam.hospital.domain;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
public interface HospitalRepository extends JpaRepository<Hospital, Long>, QuerydslPredicateExecutor<Hospital> {
    Optional<Hospital> findByUserUserSeq(Long userSeq);
    List<Hospital> findAllByUserUserSeq(Long userSeq);
    List<Hospital> findAllByIsApprovedFalse();
    List<Hospital> findByIsApprovedTrue();
    @Query(value = "select r from ReviewBoard r where r.hospital.user.name = (select u.name from User u where u.userSeq = :userSeq)")
    List<ReviewBoard> findReviewsByUserSeq(@Param("userSeq") Long userSeq);
    @Query(value = "select r from ReviewBoard r where r.hospital.user.name = (select u.name from User u where u.userSeq = (select h.user.userSeq from Hospital h where h.hospitalSeq = :hospitalSeq))")
    List<ReviewBoard> findReviewsByHospitalSeq(@Param("hospitalSeq") Long hospitalSeq);
    @Query(value = "select d from Doctor d where d.hospital.hospitalSeq = (select h.hospitalSeq from Hospital h where h.user.userSeq = :userSeq)")
    Optional<List<Doctor>> findDoctorByUserSeq(@Param("userSeq") Long userSeq);
    @Query(value = "select d from Doctor d where d.hospital.hospitalSeq = :hospitalSeq")
    Optional<List<Doctor>> findDoctorByHospitalSeq(@Param("hospitalSeq") Long hospitalSeq);
    @Query(value = "select round(avg(r.score), 2) from ReviewBoard r where r.hospital.user.name = (select u.name from User u where u.userSeq = (select h.user.userSeq from Hospital h where h.hospitalSeq = :hospitalSeq))")
    Optional<Double> findAvgByHospitalSeq(Long hospitalSeq);
    @Query(value = "select count(r.score) from ReviewBoard r where r.hospital.user.name = (select u.name from User u where u.userSeq = (select h.user.userSeq from Hospital h where h.hospitalSeq = :hospitalSeq))")
    int countByHospitalSeq(Long hospitalSeq);
    @Query(value = "select h.hospitalSeq from Hospital h where h.user.name = :hospitalName")
    Long findHospitalSeqByName(String hospitalName);
}