package com.ssafy.lam.requestboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto {
    private Long userSeq; // 응답하는 사용자의 식별자
    private String message; // 응답 메시지

    // 생성자, Getter, Setter 등은 Lombok 어노테이션으로 대체 가능
    public ResponseDto(Long userSeq, String message) {
        this.userSeq = userSeq;
        this.message = message;
    }
}