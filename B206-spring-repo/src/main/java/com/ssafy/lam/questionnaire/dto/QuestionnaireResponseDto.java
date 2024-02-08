package com.ssafy.lam.questionnaire.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class QuestionnaireResponseDto {
    private Long seq;
    private String blood;
    private String remark;
    private String content;
    private String title;

    private String base64;

    @Builder
    public QuestionnaireResponseDto(Long seq, String blood, String remark, String content, String title, String base64) {
        this.seq = seq;
        this.blood = blood;
        this.remark = remark;
        this.content = content;
        this.title = title;
        this.base64 = base64;
    }
}
