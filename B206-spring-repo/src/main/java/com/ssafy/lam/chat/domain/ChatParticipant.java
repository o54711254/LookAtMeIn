package com.ssafy.lam.chat.domain;

import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "chat_participant")
public class ChatParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_seq")
    private Long participantSeq;
//    private String userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_seq")
    private ChatRoom chatRoom;

    @Builder
    public ChatParticipant(Long participantSeq, User user, ChatRoom chatRoom) {
        this.participantSeq = participantSeq;
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
