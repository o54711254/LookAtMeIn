package com.ssafy.lam.reviewBoard.domain;

import com.ssafy.lam.hospital.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Long> {
    List<ReviewBoard> findByIsdeletedFalse();
    List<ReviewBoard> findByComplainTrue();
    @Query(value = "select u.name from User u where u.userSeq = (select h.user.userSeq from Hospital h where h.hospitalSeq = :hospitalSeq)")
    String findHospitalNameByHospitalSeq(Long hospitalSeq);

    @Query(value = "select d.docInfoName from Doctor d where d.docInfoSeq = :doctorSeq")
    String findDoctorNameByDoctorSeq(Long doctorSeq);
}
