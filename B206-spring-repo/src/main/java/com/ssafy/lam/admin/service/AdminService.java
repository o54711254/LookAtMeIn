package com.ssafy.lam.admin.service;

import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AdminService {

//    List<Freeboard> getFreeboardAll();
//
//    List<ReviewBoard> getReviewAll();
//
//    List<Hospital> getApproveHosAll();
//
//    List<Hospital> getNotApproveHosAll();

    List<Freeboard> findComplainedFreeboards();

}
