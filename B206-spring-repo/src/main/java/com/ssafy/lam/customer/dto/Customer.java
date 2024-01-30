package com.ssafy.lam.customer.dto;

import com.ssafy.lam.user.dto.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
@DiscriminatorValue("1")
public class Customer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_info_seq")
    private Long seq;
    @Column(name = "client_info_name")
    private String name;
    @Column(name = "client_info_gender")
    private String gender;
    @Column(name = "client_info_birth")
    private String birth;
    @Column(name = "client_info_tel")
    private String tel;
    @Column(name = "client_info_email")
    private String email;
    @Column(name = "client_info_address")
    private String address;
    @Column(name = "client_info_report")
    private int report;

    @OneToOne
    @JoinColumn(name = "member_seq")
    private User user;

    @Builder
    public Customer(Long seq, String name, String gender, String birth, String tel, String email, String address,
                    int report, User user) {
        this.seq = seq;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.report = report;
        this.user = user;
    }

    public Customer toEntity(Long seq, String name, String gender, String birth, String tel, String email, String address,
                             int report, User user) {
        return Customer.builder()
                .seq(seq)
                .name(name)
                .gender(gender)
                .birth(birth)
                .tel(tel)
                .email(email)
                .address(address)
                .report(report)
                .user(user)
                .build();
    }

}
