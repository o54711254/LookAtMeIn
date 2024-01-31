package com.ssafy.lam.reserve.domain;

import com.ssafy.lam.user.domain.CoordInfo;
import com.ssafy.lam.user.domain.CustomerInfo;
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
    private CustomerInfo customerInfo;

    @ManyToOne
    @JoinColumn(name = "coord_info_seq")
    private CoordInfo coordInfo;

    @Builder
    public Reserve(Long reserveTime, CustomerInfo customerInfo, CoordInfo coordInfo) {
        this.reserveTime = reserveTime;
        this.customerInfo = customerInfo;
        this.coordInfo = coordInfo;
    }
}