package com.ssafy.lam.questionnaire.service;


import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.service.UploadFileService;
import com.ssafy.lam.questionnaire.domain.QuesionnareRepository;
import com.ssafy.lam.questionnaire.domain.Questionnaire;
import com.ssafy.lam.questionnaire.dto.QuestionnaireRequestDto;
import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.domain.ReserveRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {
    private Logger log = LoggerFactory.getLogger(QuestionnaireService.class);
    @Autowired
    private QuesionnareRepository quesionnareRepository;
    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private ReserveRepository reserveRepository;


    @Transactional
    public Questionnaire createQuestionnaire(QuestionnaireRequestDto questionRequestDto, MultipartFile file) {
        UploadFile uploadFile = null;
        if(file != null)
            uploadFile = uploadFileService.store(file);

        log.info("문진서 등록 정보 : {}", questionRequestDto.getReserveSeq());
        Reserve reserve = reserveRepository.findById(questionRequestDto.getReserveSeq())
                .orElseThrow(() -> new IllegalArgumentException("없는 예약임 : " + questionRequestDto.getReserveSeq()));


        Questionnaire questionnaire = Questionnaire.builder()
                .blood(questionRequestDto.getQuestionnaire_blood())
                .remark(questionRequestDto.getQuestionnaire_remark())
                .title(questionRequestDto.getQuestionnaire_title())
                .content(questionRequestDto.getQuestionnaire_content())
                .uploadFile(uploadFile)

                .build();

        reserve.setQuestionnaire(questionnaire);
        reserve.setQuestionOk(true);

        Questionnaire saveQuesionnaire = quesionnareRepository.save(questionnaire);
        reserveRepository.save(reserve);

        return saveQuesionnaire;

    }

    public Questionnaire getQuestionnaireDetail(Long questionSeq) {
        Questionnaire questionnaire = quesionnareRepository.findById(questionSeq)
                .orElseThrow(() -> new IllegalArgumentException("없는 문진서임 : " + questionSeq));

        return questionnaire;
    }
}
