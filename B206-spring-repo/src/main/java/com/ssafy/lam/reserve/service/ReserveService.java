package com.ssafy.lam.reserve.service;

import com.ssafy.lam.questionnaire.dto.QuestionnaireRequestDto;
import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.dto.ReserveRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReserveService {


    //=================== INSERT ===================
    Reserve saveReserve(ReserveRequestDto dto);


    //=================== READ ===================
    List<ReserveResponseDto> findByUserSeq(Long userSeq);

    // ===================== 상세 조회 =====================
    Reserve getDetailReserveNotCompleted(Long reserveSeq);

    //=================== DELETE ===================
    void deleteReserve(Long reserveSeq);

    void complete(ReserveRequestDto reserveRequestDto,
                  QuestionnaireRequestDto questionnaireRequestDto,
                  MultipartFile beforeImg,
                  MultipartFile afterImg);


    List<ReserveResponseDto> getAllByUserSeqCompleted(Long userSeq);

    ReserveResponseDto getDetailReseveCompleted(Long reserveSeq);

}