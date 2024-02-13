package com.ssafy.lam.reserve.dto;


import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReserveSaveRequestDto {
    private int year;
    private int month;
    private int day;
    private String dayofweek;
    private int time;
    private long customerUserSeq;
    private long hospitalUserSeq;
    private int reserveType;

    public Reserve toEntity(User customer, User hospital) {
        return Reserve.builder()
                .customer(customer)
                .hospital(hospital)
                .year(this.year)
                .month(this.month)
                .day(this.day)
                .dayofweek(this.dayofweek)
                .time(this.time)
                .reserveType(this.reserveType)
                .build();
    }
}