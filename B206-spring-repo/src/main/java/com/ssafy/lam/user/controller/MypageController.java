package com.ssafy.lam.user.controller;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.customer.service.CustomerService;
import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.dto.FreeboardResponseDto;
import com.ssafy.lam.freeboard.service.FreeboardService;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.HospitalDto;
import com.ssafy.lam.hospital.service.HospitalService;
import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.service.ReserveService;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.dto.ReviewListDisplay;
import com.ssafy.lam.reviewBoard.service.ReviewBoardService;
import com.ssafy.lam.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypage")
public class MypageController {

    private final UserService userService;
    private final CustomerService customerService;
    private final HospitalService hospitalService;
    private final FreeboardService freeboardService;
    private final ReviewBoardService reviewBoardService;
    private final ReserveService reserveService;

    @GetMapping("/{userSeq}")
    @Operation(summary = "유저 타입에 따라 고객 정보나 병원 정보를 조회")
    public ResponseEntity<?> getMypageByUser(@PathVariable long userSeq) {
        String role = userService.getUser(userSeq).getUserType();

        if (role.equals("CUSTOMER")) {
            CustomerDto dto = customerService.getCustomer(userSeq);
            if (dto != null) {
                return new ResponseEntity<CustomerDto>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            HospitalDto dto = hospitalService.getHospital(userSeq);
            if (dto != null) {
                return new ResponseEntity<HospitalDto>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }

    @PutMapping("/user/{userSeq}")
    @Operation(summary = "고객 정보 수정")
    public ResponseEntity<?> updateCustomer(@PathVariable long userSeq, @RequestBody CustomerDto customerDto) {
        Customer customerSaveDto = customerService.updateCustomer(userSeq, customerDto);
        return new ResponseEntity<Customer>(customerSaveDto, HttpStatus.OK);
    }

    @PutMapping("/hospital/{userSeq}")
    @Operation(summary = "병원 정보 수정.")
    public ResponseEntity<?> updateHospital(@PathVariable long userSeq, @RequestBody HospitalDto hospitalDto) {
        Hospital hospitalSaveDto = hospitalService.updateHospital(userSeq, hospitalDto);
        return new ResponseEntity<Hospital>(hospitalSaveDto, HttpStatus.OK);
    }

    @GetMapping("/free/{userSeq}")
    @Operation(summary = "고객이 작성한 자유게시판 조회")
    public ResponseEntity<?> getFreeboard(@PathVariable long userSeq) {
        List<FreeboardResponseDto> boardList = freeboardService.getFreeboardByUserSeq(userSeq);
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @GetMapping("/review/{userSeq}")
    @Operation(summary = "고객이 작성한 리뷰게시판 조회")
    public ResponseEntity<?> getReviewBoard(@PathVariable long userSeq) {
        List<ReviewListDisplay> reviewList = reviewBoardService.getReviewByUserSeq(userSeq);
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("/reserve/{userSeq}")
    @Operation(summary = "고객이 예약한 상담내역")
    public ResponseEntity<?> getReserve(@PathVariable long userSeq) {
        List<ReserveResponseDto> reserveList = reserveService.findByUserSeq(userSeq);
        return new ResponseEntity<>(reserveList, HttpStatus.OK);
    }

}
