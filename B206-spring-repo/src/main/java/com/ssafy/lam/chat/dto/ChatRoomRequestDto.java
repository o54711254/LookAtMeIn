package com.ssafy.lam.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


public class ChatRoomRequestDto {
    @JsonProperty("hospitalSeq")
    private Long hospitalSeq;
    @JsonProperty("customerSeq")
    private Long customerSeq;

    @Builder
    public ChatRoomRequestDto(Long hospitalSeq, Long customerSeq) {
        this.hospitalSeq = hospitalSeq;
        this.customerSeq = customerSeq;
    }

    public ChatRoomRequestDto() {
    }

    public Long getHospitalSeq() {
        return hospitalSeq;
    }

    public void setHospitalSeq(Long hospitalSeq) {
        this.hospitalSeq = hospitalSeq;
    }

    public Long getCustomerSeq() {
        return customerSeq;
    }

    public void setCustomerSeq(Long customerSeq) {
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

