package com.ssafy.lam.reserve.service;

import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.service.UploadFileService;
import com.ssafy.lam.questionnaire.domain.QuesionnareRepository;
import com.ssafy.lam.questionnaire.domain.Questionnaire;
import com.ssafy.lam.questionnaire.dto.QuestionnaireRequestDto;
import com.ssafy.lam.reserve.domain.PastReserve;
import com.ssafy.lam.reserve.domain.PastReserveRepository;
import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.dto.PastReserveRequestDto;
import com.ssafy.lam.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PastReserveService {
    private final PastReserveRepository pastReserveRepository;
    private final QuesionnareRepository quesionnareRepository;
    private Logger log = LoggerFactory.getLogger(PastReserveService.class);
    MultipartConfig multipartConfig = new MultipartConfig();
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();
    private final UploadFileService uploadFileService;


    public void store(PastReserveRequestDto pastReserveRequestDto,
                      QuestionnaireRequestDto questionnaireRequestDto,
                      MultipartFile beforeImg,
                      MultipartFile afterImg) {
        try {

            // 수정된 문진표 entity생성
            Questionnaire questionnaire = Questionnaire.builder()
                    .blood(questionnaireRequestDto.getQuestionnaire_blood())
                    .title(questionnaireRequestDto.getQuestionnaire_title())
                    .content(questionnaireRequestDto.getQuestionnaire_content())
                    .remark(questionnaireRequestDto.getQuestionnaire_remark())
                    .build();

            // 수정된 문진표 저장
            questionnaire = quesionnareRepository.save(questionnaire);


            Reserve reserve = Reserve.builder()
                    .reserveSeq(pastReserveRequestDto.getReserveSeq())
                    .build();


            UploadFile beforeFile = uploadFileService.store(beforeImg);
            UploadFile afterFile = uploadFileService.store(afterImg);

            PastReserve pastReserve = PastReserve.builder()
                    .reserve(reserve)
                    .beforeImg(beforeFile)
                    .afterImg(afterFile)
                    .questionnaire(questionnaire)
                    .pContent(pastReserveRequestDto.getContent())
                    .pPrice(pastReserveRequestDto.getPrice())
                    .year(pastReserveRequestDto.getYear())
                    .month(pastReserveRequestDto.getMonth())
                    .day(pastReserveRequestDto.getDay())
                    .dayofweek(pastReserveRequestDto.getDayofweek())
                    .time(pastReserveRequestDto.getTime())
                    .build();

            pastReserveRepository.save(pastReserve);


        } catch (Exception e) {
            log.error("상담 종료 실패 : {}", e.getMessage());
            throw new RuntimeException("상담 종료 실패");
        }
    }
}
