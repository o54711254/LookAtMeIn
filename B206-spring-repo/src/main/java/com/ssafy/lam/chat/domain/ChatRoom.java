package com.ssafy.lam.chat.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoom_seq")
    private Long chatroomSeq;

    private Boolean deleted;

    @Builder
    public ChatRoom(Long chatroomSeq) {
        this.chatroomSeq = chatroomSeq;
    }



}
