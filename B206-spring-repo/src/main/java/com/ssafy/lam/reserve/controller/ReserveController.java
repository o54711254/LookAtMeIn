package com.ssafy.lam.reserve.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.questionnaire.domain.Questionnaire;
import com.ssafy.lam.questionnaire.dto.QuestionnaireRequestDto;
import com.ssafy.lam.questionnaire.dto.QuestionnaireResponseDto;
import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.dto.ReserveRequestDto;
import com.ssafy.lam.reserve.service.ReserveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reserve")
public class ReserveController {

    private final ReserveService reserveService;

    MultipartConfig multipartConfig = new MultipartConfig();
    // 파일이 업로드될 디렉토리 경로
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();


    Logger log = LoggerFactory.getLogger(ReserveController.class);

    // ===================== 등 록 =====================
    //클라이언트에서는 예약 시간을 Long 타입의 타임스탬프 형식으로 전송해야 함 예를 들어, 자바스크립트에서 현재 시간을 타임스탬프로 전송하는 예시
    //const reserveTime = new Date().getTime();
    @PostMapping
    @Operation(summary = "상담등록")
    public ResponseEntity<?> createReserve(@RequestBody ReserveRequestDto dto) {
        reserveService.saveReserve(dto);
        return ResponseEntity.ok().build();
    }


    // ===================== 조 회 =====================

    @GetMapping("/user/{userSeq}")
    @Operation(summary = "상담예약 전체 가져오기")
    public ResponseEntity<List<ReserveResponseDto>> getAllReservesByUser(@PathVariable long userSeq) {
        List<ReserveResponseDto> reserves = reserveService.findByUserSeq(userSeq);
        return ResponseEntity.ok(reserves);
    }

    // ===================== 상세 조회 =====================
    @GetMapping("/detail/{reserveSeq}")
    @Operation(summary = "상담예약 상세보기")
    public ResponseEntity<ReserveResponseDto> getReserveDetail(@PathVariable Long reserveSeq) {
        Reserve reserve = reserveService.getDetailReserveNotCompleted(reserveSeq);
        log.info("reserve : " + reserve.getSeq());
        ReserveResponseDto responseDto = ReserveResponseDto.builder()
                .reserveSeq(reserve.getSeq())
                .customerUserSeq(reserve.getCustomer().getUserSeq())
                .hospitalName(reserve.getHospital().getName())
                .customerName(reserve.getCustomer().getName())
                .hospitalUserSeq(reserve.getHospital().getUserSeq())
                .year(reserve.getYear())
                .month(reserve.getMonth())
                .day(reserve.getDay())
                .dayofweek(reserve.getDayofweek())
                .time(reserve.getTime())
                .build();


        Questionnaire questionnaire = reserve.getQuestionnaire();
        if(questionnaire != null){
            log.info("questionnaire : " + questionnaire.getSeq());

            QuestionnaireResponseDto questionnaireResponseDto = QuestionnaireResponseDto.builder()
                    .reserveSeq(questionnaire.getReserve().getSeq())
                    .questionnaireSeq(questionnaire.getSeq())
                    .blood(questionnaire.getBlood())
                    .title(questionnaire.getTitle())
                    .remark(questionnaire.getRemark())
                    .build();

            if(questionnaire.getUploadFile() != null){
                try{
                    Path path = Paths.get(uploadPath +"/"+ questionnaire.getUploadFile().getName());
                    String encodeFile = EncodeFile.encodeFileToBase64(path);
                    String type = questionnaire.getUploadFile().getType();
                    questionnaireResponseDto.setBase64("data:"+type+";base64,"+encodeFile);
                }catch (Exception e){
                    log.error("문진서 이미지를 찾을 수 없습니다.");
                }
            }

            responseDto.setQuestionnaireResponseDto(questionnaireResponseDto);
            log.info("questionnaireResponseDto : " + questionnaireResponseDto);
        }




        return ResponseEntity.ok(responseDto);
    }


    // ===================== 삭 제 =====================
    @DeleteMapping("/{reserveSeq}")
    @Operation(summary = "상담내역삭제")
    public ResponseEntity<?> deleteReserve(@PathVariable Long reserveSeq) {
        reserveService.deleteReserve(reserveSeq);
        return ResponseEntity.ok().build();
    }


    /**
     * @Param reserveData : 상담예약 seq, 가격, 날짜, 시간, 요일
     * @Param questionnareData : 문진표 수정 정보와 수정하려는 문진표의 seq
     * @Param beforeImg : 성형 전 이미지
     * @Param afterImg : 성형 후 이미지
     *
     */
    @PostMapping("/complete")
    @Operation(summary = "상담완료")
    public ResponseEntity<Void> completeConsulting(@RequestParam("reserveData") String reserveData,
                                                   @RequestParam("questionnareData") String questionnareData,
                                                   @RequestParam("beforeImg") MultipartFile beforeImg,
                                                   @RequestParam("afterImg") MultipartFile afterImg) {

        try{
            ReserveRequestDto reserveRequestDto = new ObjectMapper().readValue(reserveData, ReserveRequestDto.class);
            QuestionnaireRequestDto questionnaireRequestDto = new ObjectMapper().readValue(questionnareData, QuestionnaireRequestDto.class);
            reserveService.complete(reserveRequestDto, questionnaireRequestDto, beforeImg, afterImg);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userSeq}/completed/list")
    @Operation(summary = "상담한 내역 전체 조회")
    public ResponseEntity<List<ReserveResponseDto>> getAllReservesByUserCompleted(@PathVariable Long userSeq) {
        return ResponseEntity.ok(reserveService.getAllByUserSeqCompleted(userSeq));
    }

    @GetMapping("/detail/{reserveSeq}/completed")
    @Operation(summary = "상담한 내역 상세 조회")
    public ResponseEntity<ReserveResponseDto> getReserveDetailCompleted(@PathVariable Long reserveSeq) {
        return ResponseEntity.ok(reserveService.getDetailReseveCompleted(reserveSeq));
    }

    @PutMapping("/detail/{reserveSeq}/completed/delete")
    @Operation(summary = "상담한 내역 삭제")
    public ResponseEntity<Void> deleteReserveCompleted(@PathVariable Long reserveSeq) {
        reserveService.deleteReserve(reserveSeq);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
