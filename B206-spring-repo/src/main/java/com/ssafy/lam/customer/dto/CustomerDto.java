package com.ssafy.lam.customer.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomerDto {
    private String userId;
    private String userPassword;
    private String customerName;
    private String customerGender;
    private String customerPhoneNumber;
    private String customerEmail;
    private String customerAddress;
    private String base64;
    private String type;

    @Builder

    public CustomerDto(String userId, String userPassword, String customerName, String customerGender, String customerPhoneNumber, String customerEmail, String customerAddress, String base64, String type) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.customerName = customerName;
        this.customerGender = customerGender;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.base64 = base64;
        this.type = type;
    }
}
