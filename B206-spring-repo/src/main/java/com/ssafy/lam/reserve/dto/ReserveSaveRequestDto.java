package com.ssafy.lam.reserve.dto;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.coordinator.domain.Coordinator;
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

    public Reserve toEntity(Customer customerInfo, Coordinator coordinator) {
        return Reserve.builder()
                .reserveTime(this.reserveTime)
                .customerInfo(customerInfo)
                .coordinator(coordinator)
                .build();
    }
}
