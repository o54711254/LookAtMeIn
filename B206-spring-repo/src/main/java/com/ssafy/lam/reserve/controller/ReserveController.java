package com.ssafy.lam.reserve.controller;

import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.dto.ReserveSaveRequestDto;
import com.ssafy.lam.reserve.service.ReserveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reserve")
public class ReserveController {

    private final ReserveService reserveService;

    // ===================== 등 록 =====================
    //클라이언트에서는 예약 시간을 Long 타입의 타임스탬프 형식으로 전송해야 함 예를 들어, 자바스크립트에서 현재 시간을 타임스탬프로 전송하는 예시
    //const reserveTime = new Date().getTime();
    @PostMapping
    @Operation(summary = "상담등록")
    public ResponseEntity<?> createReserve(@RequestBody ReserveSaveRequestDto dto) {
        reserveService.saveReserve(dto);
        return ResponseEntity.ok().build();
    }


    // ===================== 조 회 =====================

    @GetMapping("/user/{userSeq}")
    @Operation(summary = "상담내역가져오기")
    public ResponseEntity<List<ReserveResponseDto>> getReservesByUser(@PathVariable long userSeq) {
        List<ReserveResponseDto> reserves = reserveService.findByUserSeq(userSeq);
        return ResponseEntity.ok(reserves);
    }

    // ===================== 삭 제 =====================
    @DeleteMapping("/{reserveSeq}")
    @Operation(summary = "상담내역삭제")
    public ResponseEntity<?> deleteReserve(@PathVariable Long reserveSeq) {
        reserveService.deleteReserve(reserveSeq);
        return ResponseEntity.ok().build();
    }
}
