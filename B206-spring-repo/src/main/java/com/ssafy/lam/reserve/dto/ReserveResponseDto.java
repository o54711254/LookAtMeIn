package com.ssafy.lam.reserve.dto;

import com.ssafy.lam.reserve.domain.Reserve;
import lombok.Getter;

@Getter
public class ReserveResponseDto {
    private long reserveSeq;
    private long reserveTime;

    public ReserveResponseDto(Reserve reserve) {
        this.reserveSeq = reserve.getReserveSeq();
        this.reserveTime = reserve.getReserveTime();
    }
}
