package com.ssafy.lam.customer.dto;

import com.ssafy.lam.entity.TokenInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerTokenInfo {

    private Long seq;
    private String username;
    private String userId;
    private TokenInfo tokenInfo;

    @Builder
    public CustomerTokenInfo(Long seq, String username, String userId, TokenInfo tokenInfo) {
        this.seq = seq;
        this.username = username;
        this.userId = userId;
        this.tokenInfo = tokenInfo;
    }
}
