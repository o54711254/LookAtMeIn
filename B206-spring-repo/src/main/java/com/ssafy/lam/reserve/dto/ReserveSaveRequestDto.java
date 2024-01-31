package com.ssafy.lam.reserve.dto;

import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.user.domain.CoordInfo;
import com.ssafy.lam.user.domain.CustomerInfo;
import com.ssafy.lam.reserve.domain.Reserve;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReserveSaveRequestDto {

    private long reserveTime;
    private long customerInfoSeq;
    private long coordInfoSeq;

    public Reserve toEntity(Customer customerInfo, CoordInfo coordInfo) {
        return Reserve.builder()
                .reserveTime(this.reserveTime)
                .customerInfo(customerInfo)
                .coordInfo(coordInfo)
                .build();
    }
}
