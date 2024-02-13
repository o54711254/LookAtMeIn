package com.ssafy.lam.chat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatRoomInfoDto {
    private Long chatRoomSeq;
    private String chatRoomName;

    @Builder
    public ChatRoomInfoDto(Long chatRoomSeq, String chatRoomName) {
        this.chatRoomSeq = chatRoomSeq;
        this.chatRoomName = chatRoomName;
    }
}
