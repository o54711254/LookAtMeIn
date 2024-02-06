package com.ssafy.lam.admin.controller;

import com.ssafy.lam.admin.service.AdminService;
import com.ssafy.lam.freeboard.dto.FreeboardAdminDto;
import com.ssafy.lam.hospital.dto.HospitalAdminDto;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardAdminDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/freeComplain")
    @Operation(summary = "자유게시판에서 신고된 게시물 조회")
    public ResponseEntity<List<FreeboardAdminDto>> getComplainedAndNotDeletedFreeboards() {
        List<FreeboardAdminDto> freeboards = adminService.findComplainedAndNotDeletedFreeboards();
        return ResponseEntity.ok(freeboards);
    }

    @GetMapping("/reviewComplain")
    @Operation(summary = "리뷰게시판에서 신고된 게시물 조회")
    public ResponseEntity<List<ReviewBoardAdminDto>> getComplainedAndNotDeletedReviewBoards() {
        List<ReviewBoardAdminDto> reviewBoards = adminService.findComplainedAndNotDeletedReviewBoards();
        return ResponseEntity.ok(reviewBoards);
    }

    @GetMapping("/unapproveHos")
    @Operation(summary = "미승인 병원 목록 조회")
    public ResponseEntity<List<HospitalAdminDto>> getUnapprovHos() {
        List<HospitalAdminDto> hospitals = adminService.findUnapprovedHospitals();
        return ResponseEntity.ok(hospitals);
    }

    @GetMapping("/approveHos")
    @Operation(summary = "승인된 병원 목록")
    public ResponseEntity<?> getApproveHos() {
        List<HospitalAdminDto> hospitals = adminService.findApprovedHospitals();
        return ResponseEntity.ok(hospitals);
    }

    @PatchMapping("/approveHos/{userSeq}")
    @Operation(summary = "미승인 병원 클릭하면 승인으로 변경")
    public ResponseEntity<?> updateApproveTrue(@PathVariable Long userSeq) {
        boolean result = adminService.approveHospital(userSeq);
        if (result) {
            return ResponseEntity.ok().body("병원 승인이 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("병원 승인에 실패하였습니다.");
        }
    }

    //병원반려


}
