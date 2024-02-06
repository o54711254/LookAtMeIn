package com.ssafy.lam.user.dto;

import com.ssafy.lam.entity.TokenInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserTokenInfo {
    private String userId;
    private String userName;
    private TokenInfo tokenInfo;
    private String userType;
    private Long userSeq;

    @Builder

    public UserTokenInfo(String userId, String userName, TokenInfo tokenInfo, String userType, Long userSeq) {
        this.userId = userId;
        this.userName = userName;
        this.tokenInfo = tokenInfo;
        this.userType = userType;
        this.userSeq = userSeq;
    }
}
