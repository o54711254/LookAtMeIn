package com.ssafy.lam.questionnaire.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class QuestionnaireRequestDto {
    private Long cusomter_seq;
    private Long hospital_seq;
    private String questionnaire_blood;
    private String questionnaire_remark;
    private String questionnaire_title;
    private String questionnaire_content;


    @Builder

    public QuestionnaireRequestDto(Long cusomter_seq, Long hospital_seq, String questionnaire_blood, String questionnaire_remark, String questionnaire_title, String questionnaire_content) {
        this.cusomter_seq = cusomter_seq;
        this.hospital_seq = hospital_seq;
        this.questionnaire_blood = questionnaire_blood;
        this.questionnaire_remark = questionnaire_remark;
        this.questionnaire_title = questionnaire_title;
        this.questionnaire_content = questionnaire_content;
    }
}
