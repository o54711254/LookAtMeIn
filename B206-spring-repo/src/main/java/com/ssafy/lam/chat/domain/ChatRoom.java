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


    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "hospital_name")
    private String hospitalName;


    @Builder

    public ChatRoom(Long chatroomSeq, String customerName, String hospitalName) {
        this.chatroomSeq = chatroomSeq;
        this.customerName = customerName;
        this.hospitalName = hospitalName;
    }
}
