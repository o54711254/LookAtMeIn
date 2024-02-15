package com.ssafy.lam.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.customer.service.CustomerService;
import com.ssafy.lam.freeboard.dto.FreeboardResponseDto;
import com.ssafy.lam.freeboard.service.FreeboardService;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.HospitalDto;
import com.ssafy.lam.hospital.service.HospitalService;
import com.ssafy.lam.requestboard.dto.NotificationDto;
import com.ssafy.lam.requestboard.dto.RequestDto;
import com.ssafy.lam.requestboard.dto.ResponseDto;
import com.ssafy.lam.requestboard.service.RequestBoardService;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.service.ReserveService;
import com.ssafy.lam.reviewBoard.dto.ReviewListDisplay;
import com.ssafy.lam.reviewBoard.service.ReviewBoardService;
import com.ssafy.lam.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final RequestBoardService requestBoardService;
    private Logger log = LoggerFactory.getLogger(MypageController.class);
    @GetMapping("/{userSeq}")
    @Operation(summary = "유저 타입에 따라 고객 정보나 병원 정보를 조회")
    public ResponseEntity<?> getMypageByUser(@PathVariable long userSeq) {
        String role = userService.getUser(userSeq).getUserType();
        log.info("role : {}", role);
        if (role.equals("CUSTOMER")) {
            CustomerDto dto = customerService.getCustomer(userSeq);
            if (dto != null) {
                return new ResponseEntity<CustomerDto>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            HospitalDto dto = hospitalService.getHospital(userSeq);
            log.info("dto : {}", dto.getHospitalInfo_id());
            if (dto != null) {
                return new ResponseEntity<HospitalDto>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }

    @PutMapping("/user/{userSeq}")
    @Operation(summary = "고객 정보 수정")
    public ResponseEntity<?> updateCustomer(@PathVariable long userSeq,
                                            @RequestParam("customerData") String customerData,
                                            @RequestParam(value = "profile", required = false) MultipartFile profile){
        try{
            CustomerDto customerDto = new ObjectMapper().readValue(customerData, CustomerDto.class);
            Customer customerSaveDto = customerService.updateCustomer(userSeq, customerDto, profile);
            return new ResponseEntity<Customer>(customerSaveDto, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/hospital/{userSeq}")
    @Operation(summary = "병원 정보 수정.")
    public ResponseEntity<?> updateHospital(@PathVariable long userSeq,
                                            @RequestParam("hospitalData") String hospitalData,
                                            @RequestParam(value = "profile", required = false) MultipartFile profile){
        try{
            HospitalDto hospitalDto = new ObjectMapper().readValue(hospitalData, HospitalDto.class);
            Hospital hospitalSaveDto = hospitalService.updateHospital(userSeq, hospitalDto, profile);
            return new ResponseEntity<Hospital>(hospitalSaveDto, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

    @GetMapping("/request/{userSeq}")
    @Operation(summary = "제안을 수락한 병원 목록")
    public ResponseEntity<?> getResponse(@PathVariable long userSeq) {
        List<ResponseDto> responseDtos = requestBoardService.findAllresponse(userSeq);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/myrequest/{userSeq}")
    @Operation(summary = "고객이 작성한 상담 요청 게시판 목록")
    public ResponseEntity<?> getRequest(@PathVariable long userSeq) {
        List<RequestDto> requestDtos = requestBoardService.findAllRequestByUser(userSeq);
        return new ResponseEntity<>(requestDtos, HttpStatus.OK);
    }

    @GetMapping("/notifications/{userSeq}")
    @Operation(summary = "제안을 한 병원 조회")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUser(@PathVariable Long userSeq) {
        List<NotificationDto> notifications = requestBoardService.findAllNotificationsByUser(userSeq);
        return ResponseEntity.ok(notifications);
    }
}
