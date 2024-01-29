package com.ssafy.lam.reserve.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReserveUpdateRequestDto {
    private Long reserveTime;

    @Builder
    public ReserveUpdateRequestDto(Long reserveTime) {
        this.reserveTime = reserveTime;
    }
}
