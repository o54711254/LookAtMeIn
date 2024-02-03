package com.ssafy.lam.chat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatMessageDto {
    private Long chatroomSeq;

    private String sender;
    private Long senderSeq;

    private String message;
    private Long messageSeq;



    @Builder

    public ChatMessageDto(Long chatroomSeq, String sender, Long senderSeq, String message, Long messageSeq) {
        this.chatroomSeq = chatroomSeq;
        this.sender = sender;
        this.senderSeq = senderSeq;
        this.message = message;
        this.messageSeq = messageSeq;
    }
}
