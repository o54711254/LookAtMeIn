package com.ssafy.lam.requestboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotificationDto {
    private Long seq;
    private String sender;
    private String hospitalName;
    private String message;
    private boolean isRead;

    @Builder
    public NotificationDto(Long seq, String sender, String hospitalName, String message, boolean isRead) {
        this.seq = seq;
        this.sender = sender;
        this.hospitalName = hospitalName;
        this.message = message;
        this.isRead = isRead;
    }
}
