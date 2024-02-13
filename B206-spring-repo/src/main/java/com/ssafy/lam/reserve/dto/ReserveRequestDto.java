package com.ssafy.lam.reserve.dto;


import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.user.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReserveRequestDto {
    private Long reserveSeq;
    private int year;
    private int month;
    private int day;
    private String dayofweek;
    private int time;
    private long customerUserSeq;
    private long hospitalUserSeq;

    private int price;
    private String content;
    private boolean questionnaired;

    @Builder
    public ReserveRequestDto(Long reserveSeq, int year, int month, int day, String dayofweek, int time, long customerUserSeq, long hospitalUserSeq, int price, String content, boolean questionnaired) {
        this.reserveSeq = reserveSeq;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayofweek = dayofweek;
        this.time = time;
        this.customerUserSeq = customerUserSeq;
        this.hospitalUserSeq = hospitalUserSeq;
        this.price = price;
        this.content = content;
        this.questionnaired = questionnaired;
    }

    public Reserve toEntity(User customer, User hospital) {
        return Reserve.builder()
                .customer(customer)
                .hospital(hospital)
                .year(this.year)
                .month(this.month)
                .day(this.day)
                .dayofweek(this.dayofweek)
                .time(this.time)
                .build();
    }
}