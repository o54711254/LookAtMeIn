package com.ssafy.lam.reserve.dto;

import com.ssafy.lam.questionnaire.dto.QuestionnaireResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveResponseDto {

    private Long customerUserSeq;
    private Long hospitalUserSeq;
    private Long reserveSeq;

    private String customerName;
    private String hospitalName;

    private int year;
    private int month;
    private int day;
    private String dayofweek;
    private int time;
    private String content;
    private int price;


    private String hospitalProfileBase64;
    private String hospitalProfileType;

    private boolean completed;
    private boolean questionOk;


    private String afterImgBase64;
    private String afterImgType;

    private String beforeImgBase64;
    private String beforeImgType;

    private QuestionnaireResponseDto questionnaireResponseDto;


    @Builder

    public ReserveResponseDto(Long customerUserSeq, Long hospitalUserSeq, Long reserveSeq, String customerName, String hospitalName, int year, int month, int day, String dayofweek, int time, String content, int price, String hospitalProfileBase64, String hospitalProfileType, boolean completed, String afterImgBase64, String afterImgType, String beforeImgBase64, String beforeImgType, QuestionnaireResponseDto questionnaireResponseDto, boolean questionOk) {
        this.customerUserSeq = customerUserSeq;
        this.hospitalUserSeq = hospitalUserSeq;
        this.reserveSeq = reserveSeq;
        this.customerName = customerName;
        this.hospitalName = hospitalName;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayofweek = dayofweek;
        this.time = time;
        this.content = content;
        this.price = price;
        this.hospitalProfileBase64 = hospitalProfileBase64;
        this.hospitalProfileType = hospitalProfileType;
        this.completed = completed;
        this.afterImgBase64 = afterImgBase64;
        this.afterImgType = afterImgType;
        this.beforeImgBase64 = beforeImgBase64;
        this.beforeImgType = beforeImgType;
        this.questionnaireResponseDto = questionnaireResponseDto;
        this.questionOk = questionOk;
    }
}