package com.ssafy.lam.reserve.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PastReserveRequestDto {

    private Long reserveSeq;
    private String content;
    private int price;
    private int year;
    private int month;
    private int day;
    private String dayofweek;
    private int time;

    @Builder

    public PastReserveRequestDto(Long reserveSeq, String content, int price, int year, int month, int day, String dayofweek, int time) {
        this.reserveSeq = reserveSeq;
        this.content = content;
        this.price = price;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayofweek = dayofweek;
        this.time = time;
    }
}
