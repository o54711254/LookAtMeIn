package com.ssafy.lam.reserve.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PastReserveResponseDto {
    private Long reserveSeq;
    private Long customerUserSeq;
    private Long hospitalUserSeq;
    private int year;
    private int month;
    private int day;
    private String dayofweek;
    private int time;
    private String content;
    private int price;
    private Long questionnaireSeq;

    @Builder
    public PastReserveResponseDto(Long reserveSeq, Long customerUserSeq, Long hospitalUserSeq, int year, int month, int day, String dayofweek, int time, String content, int price, Long questionnaireSeq) {
        this.reserveSeq = reserveSeq;
        this.customerUserSeq = customerUserSeq;
        this.hospitalUserSeq = hospitalUserSeq;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayofweek = dayofweek;
        this.time = time;
        this.content = content;
        this.price = price;
        this.questionnaireSeq = questionnaireSeq;
    }
}
