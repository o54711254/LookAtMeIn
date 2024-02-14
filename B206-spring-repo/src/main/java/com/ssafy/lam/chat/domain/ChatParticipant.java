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
@Table(name = "chat_participant")
@NoArgsConstructor

public class ChatParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_seq")
    private Long participantSeq;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_seq")
    private ChatRoom chatRoom;

    private String chatRoomName;

    @Builder
    public ChatParticipant(Long participantSeq, User user, ChatRoom chatRoom, String chatRoomName) {
        this.participantSeq = participantSeq;
        this.user = user;
        this.chatRoom = chatRoom;
        this.chatRoomName = chatRoomName;
    }
}
