package com.ssafy.lam.reserve.dto;

import com.ssafy.lam.reserve.domain.Reserve;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReserveSaveRequestDto {
    private long reserveTime;
    private int reserveType;
    private long clientInfoSeq;
    private long coordinatorSeq;

    @Builder
    public ReserveSaveRequestDto(long reserveTime, int reserveType, long clientInfoSeq, long coordinatorSeq) {
        this.reserveTime = reserveTime;
        this.reserveType = reserveType;
        this.clientInfoSeq = clientInfoSeq;
        this.coordinatorSeq = coordinatorSeq;
    }

    public Reserve toEntity() {
        return Reserve.builder()
                .reserveTime(reserveTime)
                .reserveType(reserveType)
                .clientInfoSeq(clientInfoSeq)
                .coordinatorSeq(coordinatorSeq)
                .build();
    }
}
