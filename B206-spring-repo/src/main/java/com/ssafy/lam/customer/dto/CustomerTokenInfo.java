package com.ssafy.lam.customer.dto;

import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.entity.TokenInfo;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor

public class CustomerTokenInfo {
    private TokenInfo tokenInfo;
    private String userId;
    private String username;
    private String user_type;

    @Builder
    public CustomerTokenInfo(TokenInfo tokenInfo, String userId, String username, String user_type) {
        this.tokenInfo = tokenInfo;
        this.userId = userId;
        this.username = username;
        this.user_type = user_type;
    }
}
