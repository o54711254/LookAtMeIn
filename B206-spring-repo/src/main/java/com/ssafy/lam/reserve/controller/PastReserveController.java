package com.ssafy.lam.reserve.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.lam.questionnaire.dto.QuestionnaireRequestDto;
import com.ssafy.lam.questionnaire.service.QuestionnaireService;
import com.ssafy.lam.reserve.domain.PastReserve;
import com.ssafy.lam.reserve.dto.PastReserveRequestDto;
import com.ssafy.lam.reserve.dto.PastReserveResponseDto;
import com.ssafy.lam.reserve.service.PastReserveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/past-reserve")
@RequiredArgsConstructor
public class PastReserveController {

    private final QuestionnaireService questionnaireService;

    private final PastReserveService pastReserveService;
    private Logger log = LoggerFactory.getLogger(PastReserveController.class);

    /**
     * @Param pastReserveData : 상담 내용, 가격, 날짜, 시간, 요일, 문진표 수정 정보
     * @Param questionnare : 문진표 수정정보와 수정하려는 문진표의 seq
     * @Param beforeImg : 성형 전 이미지
     * @Param afterImg : 성형 후 이미지
     */
    @PostMapping("/terminate")
    @Operation(summary = "상담 종료")
    public ResponseEntity<Void> terminateConsulting(@RequestParam("pastReserveData") String pastReserveData,
                                                    @RequestParam("questionnareData") String questionnareData,
                                                    @RequestParam(value = "beforeImg", required = false) MultipartFile beforeImg,
                                                    @RequestParam(value = "afterImg", required = false) MultipartFile afterImg) {

        try{
            PastReserveRequestDto pastReserveRequestDto = new ObjectMapper().readValue(pastReserveData, PastReserveRequestDto.class);
            QuestionnaireRequestDto questionnaireRequestDto = new ObjectMapper().readValue(questionnareData, QuestionnaireRequestDto.class);
            pastReserveService.store(pastReserveRequestDto, questionnaireRequestDto, beforeImg, afterImg);

            return ResponseEntity.ok().build();
        }catch (Exception e) {
            log.error("상담 종료 실패 : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{user_seq}/list")
    @Operation(summary = "고객의 상담한 내역가져오기")
    public ResponseEntity<List<PastReserveResponseDto>> getPastReserveList(@PathVariable("user_seq") Long userSeq) {

        List<PastReserveResponseDto> pastReserveResponseDtoList =
                pastReserveService.getAllByUserSeq(userSeq).stream()
                .map(pastReserve ->{
                    return PastReserveResponseDto.builder()
                            .reserveSeq(pastReserve.getPReserveSeq())
                            .customerUserSeq(pastReserve.getCustomer().getUserSeq())
                            .hospitalUserSeq(pastReserve.getHospital().getUserSeq())
                            .year(pastReserve.getYear())
                            .month(pastReserve.getMonth())
                            .day(pastReserve.getDay())
                            .time(pastReserve.getTime())
                            .dayofweek(pastReserve.getDayofweek())
                            .content(pastReserve.getPContent())
                            .price(pastReserve.getPPrice())
                            .build();

                }).collect(Collectors.toList());

        return ResponseEntity.ok(pastReserveResponseDtoList);
    }

    @GetMapping("/detail/{pReserveSeq}")
    @Operation(summary = "상담한 내역 상세 가져오기")
    public ResponseEntity<PastReserveResponseDto> getPastReserve(@PathVariable("pReserveSeq") Long pReserveSeq){
        PastReserve pastReserve = pastReserveService.getByReserveSeq(pReserveSeq);
        PastReserveResponseDto pastReserveResponseDto = PastReserveResponseDto.builder()
                .reserveSeq(pastReserve.getPReserveSeq())
                .customerUserSeq(pastReserve.getCustomer().getUserSeq())
                .hospitalUserSeq(pastReserve.getHospital().getUserSeq())
                .year(pastReserve.getYear())
                .month(pastReserve.getMonth())
                .day(pastReserve.getDay())
                .time(pastReserve.getTime())
                .dayofweek(pastReserve.getDayofweek())
                .content(pastReserve.getPContent())
                .price(pastReserve.getPPrice())
                .build();

        return ResponseEntity.ok(pastReserveResponseDto);
    }




}
