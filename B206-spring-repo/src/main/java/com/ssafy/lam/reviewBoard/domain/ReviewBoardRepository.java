package com.ssafy.lam.reviewBoard.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Long> {
    List<ReviewBoard> findByHospitalContaining(String hospital);
    List<ReviewBoard> findByDoctorContaining(String doctor);
    List<ReviewBoard> findBySurgeryContaining(String surgery);
    List<ReviewBoard> findByRegionContaining(String region);
//    @Query(value = "select distinct r from ReviewBoard r left join Customer c where :customerSeq = c.customerSeq")
    List<ReviewBoard> findAllByCustomerCustomerSeq(long customerSeq);
//    int findByCustomerCustomerSeq(long customerSeq);

//    int findByCustomerSeq(long customerSeq);
}
