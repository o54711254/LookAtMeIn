package com.ssafy.lam.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UserDto {

    // 공통
    private String userId;
    private String userPassword;
    private String userName;



    @Builder
    public UserDto(String userId, String userPassword, String userName) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
    }
}
