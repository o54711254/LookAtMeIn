package com.ssafy.lam.reserve.controller;

import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.dto.ReserveRequestDto;
import com.ssafy.lam.reserve.service.ReserveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
        Reserve reserve = reserveService.getDetailReserve(reserveSeq);

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
                .questionnaired(reserve.getQuestionnaire() != null) // 문진표가 있는지 없는지
                .build();

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
     * @Param questionnareData : 문진표 수정정 보와 수정하려는 문진표의 seq
     * @Param beforeImg : 성형 전 이미지
     * @Param afterImg : 성형 후 이미지
     *
     */
//    public ResponseEntity<Void> terminateCosulting(@RequestParam("reserveData") String reserveData,
//                                                  @RequestParam("questionnareData") String questionnareData,
//                                                  @RequestParam("beforeImg") String beforeImg,
//                                                  @RequestParam("afterImg") String afterImg) {
//
//        try{
//            ReserveRequestDto reserveRequestDto = new ObjectMapper().readValue(reserveData, ReserveRequestDto.class);
//            QuestionnaireRequestDto questionnaireRequestDto = new ObjectMapper().readValue(questionnareData, QuestionnaireRequestDto.class);
//            reserveService.completeConsulting(reserveRequestDto, questionnaireRequestDto, beforeImg, afterImg);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

}
