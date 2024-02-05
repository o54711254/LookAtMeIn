package com.ssafy.lam.admin.controller;

import com.ssafy.lam.admin.service.AdminService;
import com.ssafy.lam.freeboard.dto.FreeboardAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

//    @GetMapping("/complainedFreeboards")
//    public ResponseEntity<List<FreeboardAdminDto>> getComplainedFreeboards() {
//        List<FreeboardAdminDto> complainedFreeboards = adminService.findComplainedFreeboards();
//        return ResponseEntity.ok(complainedFreeboards);
//    }
    //신고된 자유 게시글 목록 조회
    //return List<FreeBoard>
    //boolean처리된것만

    //신고된 후기 게시글 조회
    //위에와마찬가지로

    //등록병원목록

    //미승인병원목록

    //병원반려
}
