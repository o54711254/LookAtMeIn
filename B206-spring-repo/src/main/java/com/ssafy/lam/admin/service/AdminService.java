package com.ssafy.lam.admin.service;

import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.dto.FreeboardAdminDto;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.HospitalAdminDto;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AdminService {

    List<FreeboardAdminDto> findComplainedAndNotDeletedFreeboards();

    List<ReviewBoardAdminDto> findComplainedAndNotDeletedReviewBoards();

    List<HospitalAdminDto> findUnapprovedHospitals();

    List<HospitalAdminDto> findApprovedHospitals();

    boolean approveHospital(Long hospitalSeq);

}
