package com.ssafy.lam.reserve.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.lam.file.service.UploadFileService;
import com.ssafy.lam.questionnaire.domain.Questionnaire;
import com.ssafy.lam.questionnaire.dto.QuestionnaireRequestDto;
import com.ssafy.lam.questionnaire.service.QuestionnaireService;
import com.ssafy.lam.reserve.dto.PastReserveRequestDto;
import com.ssafy.lam.reserve.service.PastReserveService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Past;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Operation
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




}
