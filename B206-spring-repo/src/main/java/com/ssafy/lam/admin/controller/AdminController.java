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
    @PatchMapping("/disapproveHos/{userSeq}")
    @Operation(summary = "미승인 병원 반려")
    public ResponseEntity<?> rejectApprove(@PathVariable Long userSeq) {
        boolean result = adminService.disapproveHospital(userSeq);
        if (result) {
            return ResponseEntity.ok().body("병원 승인이 거절되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("병원 승인 거절에 실패하였습니다.");
        }
    }

    @PutMapping("/delete/reviewboard/{reviewboardSeq}")
    @Operation(summary = "신고받은 후기 게시판 글 삭제")
    public ResponseEntity<?> deleteReportedReviewBoard(@PathVariable Long reviewboardSeq) {
        boolean result = adminService.deactivateReviewBoard(reviewboardSeq);
        if (result) {
            return ResponseEntity.ok().body("신고된 후기 삭제가 완료되었습니다. boardSeq: " + reviewboardSeq);
        } else {
            return ResponseEntity.badRequest().body("신고된 후기 삭제를 실패하였습니다. boardSeq: " + reviewboardSeq);
        }
    }

    @PutMapping("cancelReport/reviewboard/{reviewboardSeq}")
    @Operation(summary = "신고받은 후기 게시판 글 신고 취소")
    public ResponseEntity<?> cancelReportedReviewBoard(@PathVariable Long reviewboardSeq) {
        boolean result = adminService.cancelReportReviewBoard(reviewboardSeq);
        if (result) {
            return ResponseEntity.ok().body("신고된 후기가 신고 취소되었습니다. boardSeq: " + reviewboardSeq);
        } else {
            return ResponseEntity.badRequest().body("신고된 후기가 신고 취소를 실패하였습니다. boardSeq: " + reviewboardSeq);
        }
    }

    @PutMapping("/delete/freeboard/{freeboardSeq}")
    @Operation(summary = "신고받은 자유 게시판 글 삭제")
    public ResponseEntity<?> deleteReportedFreeBoard(@PathVariable Long freeboardSeq) {
        boolean result = adminService.deactivateFreeBoard(freeboardSeq);
        if (result) {
            return ResponseEntity.ok().body("신고된 자유 게시글 삭제가 완료되었습니다. boardSeq: " + freeboardSeq);
        } else {
            return ResponseEntity.badRequest().body("신고된 자유 게시글 삭제를 실패하였습니다. boardSeq: " + freeboardSeq);
        }
    }

    @PutMapping("cancelReport/freeboard/{freeboardSeq}")
    @Operation(summary = "신고받은 자유 게시판 글 신고 취소")
    public ResponseEntity<?> cancelReportedFreeBoard(@PathVariable Long freeboardSeq) {
        boolean result = adminService.cancelReportFreeBoard(freeboardSeq);
        if (result) {
            return ResponseEntity.ok().body("신고된 자유 게시글이 신고 취소되었습니다. boardSeq: " + freeboardSeq);
        } else {
            return ResponseEntity.badRequest().body("신고된 자유 게시글 신고 취소를 실패하였습니다. boardSeq: " + freeboardSeq);
        }
    }

}
