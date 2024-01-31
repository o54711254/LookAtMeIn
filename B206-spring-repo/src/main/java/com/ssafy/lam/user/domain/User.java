package com.ssafy.lam.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter

//@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq")
    private long seq;

    @Column(name = "user_id")
    private String id;

    @Column(name = "user_pw")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public User() {
    }

    @Builder
    public User(long seq, String id, String password, List<String> roles){
        this.seq = seq;
        this.id = id;
        this.password = password;
        this.roles = roles;
    }

    public User toEntity(long seq, String id, String password, List<String> roles) {
        return User.builder()
                .seq(seq)
                .id(id)
                .password(password)
                .roles(roles)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}