package com.ssafy.lam.chat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_seq")
    private Long messageSeq;

    @Column(name = "chatroom_seq")
    private Long chatroomSeq;


    private String sender;
    // private Long senderId;
    private String message;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + messageSeq +
                ", chatRoomId=" + chatroomSeq +
                ", sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
