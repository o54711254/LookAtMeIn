package com.ssafy.lam.hospital.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HospitalDto {
    private String hospitalInfo_id;
    private String hospitalInfo_password;
    private String hospitalInfo_name;
    private String hospitalInfo_phoneNumber;
    private String hospitalInfo_email;
    private String hospitalInfo_introduce;
    private String hospitalInfo_address;
    private String hospiaalInfo_open;
    private String hospitalInfo_close;
    private String hospitalInfo_url;

    @Builder
    public HospitalDto(String hospitalInfo_id, String hospitalInfo_password, String hospitalInfo_name, String hospitalInfo_phoneNumber, String hospitalInfo_email, String hospitalInfo_introduce, String hospitalInfo_address, String hospiaalInfo_open, String hospitalInfo_close, String hospitalInfo_url) {
        this.hospitalInfo_id = hospitalInfo_id;
        this.hospitalInfo_password = hospitalInfo_password;
        this.hospitalInfo_name = hospitalInfo_name;
        this.hospitalInfo_phoneNumber = hospitalInfo_phoneNumber;
        this.hospitalInfo_email = hospitalInfo_email;
        this.hospitalInfo_introduce = hospitalInfo_introduce;
        this.hospitalInfo_address = hospitalInfo_address;
        this.hospiaalInfo_open = hospiaalInfo_open;
        this.hospitalInfo_close = hospitalInfo_close;
        this.hospitalInfo_url = hospitalInfo_url;
    }
}