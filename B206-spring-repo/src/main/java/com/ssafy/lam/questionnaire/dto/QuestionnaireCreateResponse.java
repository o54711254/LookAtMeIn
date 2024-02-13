package com.ssafy.lam.questionnaire.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionnaireCreateResponse {
    private Long questionnaireSeq;
    private Long reverseSeq;

    @Builder
    public QuestionnaireCreateResponse(Long questionnaireSeq, Long reverseSeq) {
        this.questionnaireSeq = questionnaireSeq;
        this.reverseSeq = reverseSeq;
    }
}
