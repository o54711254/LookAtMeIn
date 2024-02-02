package com.ssafy.lam.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDto {
    private Long chatroomSeq;
    private Long userSeq;
    private String message;


    @Builder
    public ChatMessageDto(Long chatroomSeq, Long userSeq, String message) {
        this.chatroomSeq = chatroomSeq;
        this.userSeq = userSeq;
        this.message = message;
    }

}
