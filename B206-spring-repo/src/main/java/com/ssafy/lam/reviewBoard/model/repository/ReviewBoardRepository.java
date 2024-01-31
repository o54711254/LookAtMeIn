package com.ssafy.lam.reviewBoard.model.repository;

import com.ssafy.lam.entity.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Long> {
    List<ReviewBoard> findByHospitalContaining(String hospital);
    List<ReviewBoard> findByDoctorContaining(String doctor);
    List<ReviewBoard> findBySurgeryContaining(String surgery);
    List<ReviewBoard> findByRegionContaining(String region);


}
