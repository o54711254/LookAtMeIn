package com.ssafy.lam.chat.entity;

import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_seq")
    private Long participantSeq;
//    private String userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(name = "chatroom_seq")
    private Long chatroomSeq;
}
