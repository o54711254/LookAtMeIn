package com.ssafy.lam.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatRoomDto {
    private Long hospitalSeq;
    private Long customerSeq;

    @Builder
    public ChatRoomDto(Long hospitalSeq, Long customerSeq) {
        this.hospitalSeq = hospitalSeq;
        this.customerSeq = customerSeq;
    }
}
