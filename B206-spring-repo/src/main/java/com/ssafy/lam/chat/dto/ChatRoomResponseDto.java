package com.ssafy.lam.chat.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatRoomResponseDto {
    private Long chatroomSeq;

    private Long customerSeq;
    private String customerId;
    private String customerName;

    private Long hospitalSeq;
    private String hospitalId;
    private String hospitalName;


    @Builder
    public ChatRoomResponseDto(Long chatroomSeq, Long customerSeq, String customerId, String customerName, Long hospitalSeq, String hospitalId, String hospitalName) {
        this.chatroomSeq = chatroomSeq;
        this.customerSeq = customerSeq;
        this.customerId = customerId;
        this.customerName = customerName;
        this.hospitalSeq = hospitalSeq;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
    }
}
