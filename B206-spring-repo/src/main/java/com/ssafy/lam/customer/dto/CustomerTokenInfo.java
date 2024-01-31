package com.ssafy.lam.customer.dto;

import com.ssafy.lam.entity.TokenInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerTokenInfo {

    private Long customerSeq;
    private String username;
    private String userId;
    private TokenInfo tokenInfo;

    @Builder
    public CustomerTokenInfo(Long customerSeq, String username, String userId, TokenInfo tokenInfo) {
        this.customerSeq = customerSeq;
        this.username = username;
        this.userId = userId;
        this.tokenInfo = tokenInfo;
    }
}
