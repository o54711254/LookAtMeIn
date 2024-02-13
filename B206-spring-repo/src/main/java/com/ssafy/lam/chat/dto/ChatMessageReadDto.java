package com.ssafy.lam.chat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatMessageReadDto {

    private Long chatroomSeq;

    private String sender;
    private Long senderSeq;

    private String message;
    private Long messageSeq;

    private String customerProfileBase64;
    private String customerProfileType;

    private String hospitalProfileBase64;
    private String hospitalProfileType;

    @Builder

    public ChatMessageReadDto(Long chatroomSeq, String sender, Long senderSeq, String message, Long messageSeq, String customerProfileBase64, String customerProfileType, String hospitalProfileBase64, String hospitalProfileType) {
        this.chatroomSeq = chatroomSeq;
        this.sender = sender;
        this.senderSeq = senderSeq;
        this.message = message;
        this.messageSeq = messageSeq;
        this.customerProfileBase64 = customerProfileBase64;
        this.customerProfileType = customerProfileType;
        this.hospitalProfileBase64 = hospitalProfileBase64;
        this.hospitalProfileType = hospitalProfileType;
    }
}



