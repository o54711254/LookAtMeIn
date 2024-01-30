package com.ssafy.lam.user.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Long seq;
    @Column(name = "member_id")
    private String userId;
    @Column(name = "member_pw")
    private String password;
    private String token;

    @Builder
    public User(Long seq, String userId, String password, String token) {
        this.seq = seq;
        this.userId = userId;
        this.password = password;
        this.token = token;
    }

    public User toEntity(Long seq, String userId, String password, String token) {
        return User.builder()
                .seq(seq)
                .userId(userId)
                .password(password)
                .token(token)
                .build();
    }

//    public Customer toEntity() {
//        return Customer.builder()
//                .seq(this.seq)
//                .id(this.id)
//                .password(this.password)
//                .token(this.token)
//                .build();
//    }

//    public Customer toEntity() {
//        return Customer.builder()
//                .seq(seq)
//                .id(id)
//                .password(password)
//                .token(token)
//                .build();
//    }

}