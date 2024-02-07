package com.ssafy.lam.reviewBoard.domain;

import com.ssafy.lam.reviewBoard.dto.ReviewListDisplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Long> {
    List<ReviewBoard> findByIsdeletedFalse();
//    List<ReviewBoard> findAllByUserUserSeqAndIsdeletedFalse(Long userSeq);

    @Query("SELECT new com.ssafy.lam.reviewBoard.dto.ReviewListDisplay(r.seq, u.name, r.title, r.cnt, r.regdate, r.score, r.doctor, r.region, r.surgery, r.hospital, r.expectedPrice, r.surgeryPrice) FROM ReviewBoard r JOIN r.user u WHERE u.userSeq = :userSeq AND r.isdeleted = false")
    List<ReviewListDisplay> findAllByUserUserSeqAndIsdeletedFalse(Long userSeq);

    List<ReviewBoard> findByComplainTrueAndIsdeletedFalse();
    List<ReviewBoard> findByComplainTrue();
    public List<ReviewBoard> findByUserUserSeqAndIsdeletedFalse(Long userSeq);
}
