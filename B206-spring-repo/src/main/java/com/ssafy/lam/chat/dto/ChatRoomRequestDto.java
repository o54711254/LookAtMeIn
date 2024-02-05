package com.ssafy.lam.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class ChatRoomRequestDto {

    private Long hospitalSeq;

    private Long customerSeq;


    public ChatRoomRequestDto(Long hospitalSeq, Long customerSeq) {
        this.hospitalSeq = hospitalSeq;
        this.customerSeq = customerSeq;
    }

    @Override
    public String toString() {
        return "ChatRoomRequestDto{" +
                "hospitalSeq=" + hospitalSeq +
                ", customerSeq=" + customerSeq +
                '}';
    }
}

