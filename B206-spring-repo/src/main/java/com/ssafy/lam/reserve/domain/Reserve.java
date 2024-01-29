package com.ssafy.lam.reserve.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reserveSeq;

    @Column
    private long reserveTime;

    @Column
    private int reserveType;

    @Column
    private long clientInfoSeq;

    @Column
    private long coordinatorSeq;

    @Builder
    public Reserve(long reserveTime, int reserveType, long clientInfoSeq, long coordinatorSeq) {
        this.reserveTime = reserveTime;
        this.reserveType = reserveType;
        this.clientInfoSeq = clientInfoSeq;
        this.coordinatorSeq = coordinatorSeq;
    }
}
