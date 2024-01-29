package com.ssafy.lam.reserve.dto;

import com.ssafy.lam.reserve.domain.Reserve;
import lombok.Getter;

@Getter
public class ReserveResponseDto {
    private Long reserveSeq;
    private Long reserveTime;
    private int reserveType;
    private long clientInfoSeq;
    private long coordinatorSeq;

    public ReserveResponseDto(Reserve entity) {
        this.reserveSeq = reserveSeq;
        this.reserveTime = reserveTime;
        this.reserveType = reserveType;
        this.clientInfoSeq = clientInfoSeq;
        this.coordinatorSeq = coordinatorSeq;
    }
}
