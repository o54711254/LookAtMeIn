package com.ssafy.lam.reserve.controller;

import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.dto.ReserveSaveRequestDto;
import com.ssafy.lam.reserve.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReserveController {

    private final ReserveService reserveService;


    // ===================== 등 록 =====================
    @PostMapping("/reserve")
    public ResponseEntity<?> save(@RequestBody ReserveSaveRequestDto requestDto) {
        try {
            long result = reserveService.save(requestDto);
            if (result != 0) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("실패", HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== 조 회 =====================
    // 코디가 자신에게 예약한 사람들(타임테이블) 조회
    @GetMapping("/reserve/{coordinatorSeq}")
    public ResponseEntity<?> findAllById(@PathVariable long coordinatorSeq) {
        try {
            List<Reserve> list = reserveService.findAllById(coordinatorSeq);
            if (!list.isEmpty()) {
                return new ResponseEntity<List<Reserve>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("실패", HttpStatus.BAD_REQUEST);
        }
    }

    // 한명조회
    @GetMapping("/reserve/{coordinatorSeq}")
    public ResponseEntity<?> findById(@PathVariable long coordinatorSeq) {
        try {
            ReserveResponseDto reserve = reserveService.findById(coordinatorSeq);
            if (reserve != null) {
                return new ResponseEntity<>(reserve, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("실패", HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== 삭 제 =====================
    @DeleteMapping("/api/v1/reserve/{reserveSeq}")
    public Long delete(@PathVariable Long reserveSeq) {
        reserveService.delete(reserveSeq);
        return reserveSeq;
    }
}
