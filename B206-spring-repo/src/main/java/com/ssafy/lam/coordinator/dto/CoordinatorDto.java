package com.ssafy.lam.coordinator.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class CoordinatorDto {
    private String userId;
    private String password;
    private String name;

    @Builder
    public CoordinatorDto(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }
}
