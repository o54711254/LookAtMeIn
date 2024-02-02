package com.ssafy.lam.chat.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoom_seq")
    private Long chatroomSeq;


    @Column(name="chatRoom_name")
    private String chatroomName;

    @Builder
    public ChatRoom(Long chatroomSeq, String chatroomName) {
        this.chatroomSeq = chatroomSeq;
        this.chatroomName = chatroomName;
    }
}
