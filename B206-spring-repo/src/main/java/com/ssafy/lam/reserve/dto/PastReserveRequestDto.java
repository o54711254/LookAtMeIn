package com.ssafy.lam.reserve.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PastReserveRequestDto {

    private Long pastReserveSeq;
    private String content;
    private int price;
    private int year;
    private int month;
    private int day;
    private String dayofweek;
    private int time;
    private Long customerSeq;
    private Long hospitalSeq;


    @Builder

    public PastReserveRequestDto(Long pastReserveSeq, String content, int price, int year, int month, int day, String dayofweek, int time, Long customerSeq, Long hospitalSeq) {
        this.pastReserveSeq = pastReserveSeq;
        this.content = content;
        this.price = price;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayofweek = dayofweek;
        this.time = time;
        this.customerSeq = customerSeq;
        this.hospitalSeq = hospitalSeq;
    }
}
