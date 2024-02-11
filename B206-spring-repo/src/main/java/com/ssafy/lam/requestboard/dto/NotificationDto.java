package com.ssafy.lam.requestboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationDto {
    private Long seq;
    private String hospitalName;
    private String message;
    private boolean isRead;

    @Builder
    public NotificationDto(Long seq, String hospitalName, String message, boolean isRead) {
        this.seq = seq;
        this.hospitalName = hospitalName;
        this.message = message;
        this.isRead = isRead;
    }
}
