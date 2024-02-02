package com.ssafy.lam.reserve.domain;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.coordinator.domain.Coordinator;
import com.ssafy.lam.hospital.domain.Hospital;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reserve")
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveSeq;

    private Long reserveTime;

    @ManyToOne
    @JoinColumn(name = "customer_info_seq")
    private Customer customerInfo;
//    private CustomerInfo customerInfo;

    @ManyToOne
    @JoinColumn(name = "hos_info_seq")
    private Hospital hospital;

    @Builder
    public Reserve(Long reserveTime, Customer customerInfo, Hospital hospital) {
        this.reserveTime = reserveTime;
        this.customerInfo = customerInfo;
        this.hospital = hospital;
    }
}