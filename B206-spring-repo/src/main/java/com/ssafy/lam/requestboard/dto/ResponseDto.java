package com.ssafy.lam.requestboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto {
    private Long seq;
    private Long userSeq; // 제안하는 사람
    private String hospitalName;
    private String message; // 제안 메시지

    public ResponseDto(Long seq, Long userSeq, String hospitalName, String message) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.hospitalName = hospitalName;
        this.message = message;
    }
}