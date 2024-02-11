package com.ssafy.lam.questionnaire.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class QuestionnaireRequestDto {

    private String questionnaire_blood;
    private String questionnaire_remark;
    private String questionnaire_title;
    private String questionnaire_content;


    @Builder

    public QuestionnaireRequestDto(String questionnaire_blood, String questionnaire_remark, String questionnaire_title, String questionnaire_content) {
        this.questionnaire_blood = questionnaire_blood;
        this.questionnaire_remark = questionnaire_remark;
        this.questionnaire_title = questionnaire_title;
        this.questionnaire_content = questionnaire_content;
    }
}
