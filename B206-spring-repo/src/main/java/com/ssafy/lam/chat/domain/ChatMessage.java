package com.ssafy.lam.chat.domain;

import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_seq")
    private Long messageSeq;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_seq")
    private ChatRoom chatroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chatMessage_sender")
    private User user;
    private String message;

    boolean deleted;

    @Builder
    public ChatMessage(Long messageSeq, ChatRoom chatroom, User user, String message, boolean deleted) {
        this.messageSeq = messageSeq;
        this.chatroom = chatroom;
        this.user = user;
        this.message = message;
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "messageSeq=" + messageSeq +
                ", chatRoomSeq=" + chatroom.getChatroomSeq() +
                ", sender='" + user.getName() + '\'' +
                ", sender_seq='" + user.getUserSeq() + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
