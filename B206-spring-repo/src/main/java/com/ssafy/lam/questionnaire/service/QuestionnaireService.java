package com.ssafy.lam.questionnaire.service;

import com.ssafy.lam.questionnaire.domain.QuesionnareRepository;
import com.ssafy.lam.questionnaire.domain.Questionnaire;
import com.ssafy.lam.questionnaire.dto.QuestionnaireRequestDto;
import com.ssafy.lam.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {
    private Logger log = LoggerFactory.getLogger(QuestionnaireService.class);
    private QuesionnareRepository quesionnareRepository;


    public Questionnaire createQuestionnaire(QuestionnaireRequestDto questionRequestDto) {
        User customer = User.builder().userSeq(questionRequestDto.getCusomter_seq()).build();
        User hospital = User.builder().userSeq(questionRequestDto.getHospital_seq()).build();

        Questionnaire questionnaire = Questionnaire.builder()
                .customer(customer)
                .hospital(hospital)
                .blood(questionRequestDto.getQuestionnaire_blood())
                .remark(questionRequestDto.getQuestionnaire_remark())
                .title(questionRequestDto.getQuestionnaire_title())
                .content(questionRequestDto.getQuestionnaire_content())
                .build();

        Questionnaire saveQuesionnaire = quesionnareRepository.save(questionnaire);
        return saveQuesionnaire;

    }

}
