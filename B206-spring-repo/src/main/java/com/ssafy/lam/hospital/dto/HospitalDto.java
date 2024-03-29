package com.ssafy.lam.hospital.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HospitalDto {
    private Long hospitalInfo_seq;
    private String hospitalInfo_id;
    private String hospitalInfo_password;
    private String hospitalInfo_name;
    private String hospitalInfo_phoneNumber;
    private String hospitalInfo_email;
    private String hospitalInfo_introduce;
    private String hospitalInfo_address;
    private String hospitalInfo_open;
    private String hospitalInfo_close;
    private String hospitalInfo_url;
    private boolean hospitalInfo_rejected;
    private List<CategoryDto> hospitalInfo_category;

    private String hospitalProfileBase64;
    private String hospitalProfileType;



    @Builder
    public HospitalDto(Long hospitalInfo_seq, String hospitalInfo_id, String hospitalInfo_password, String hospitalInfo_name, String hospitalInfo_phoneNumber, String hospitalInfo_email, String hospitalInfo_introduce, String hospitalInfo_address, String hospitalInfo_open, String hospitalInfo_close, String hospitalInfo_url, boolean hospitalInfo_rejected, List<CategoryDto> hospitalInfo_category, String hospitalProfileBase64, String hospitalProfileType) {
        this.hospitalInfo_seq = hospitalInfo_seq;
        this.hospitalInfo_id = hospitalInfo_id;
        this.hospitalInfo_password = hospitalInfo_password;
        this.hospitalInfo_name = hospitalInfo_name;
        this.hospitalInfo_phoneNumber = hospitalInfo_phoneNumber;
        this.hospitalInfo_email = hospitalInfo_email;
        this.hospitalInfo_introduce = hospitalInfo_introduce;
        this.hospitalInfo_address = hospitalInfo_address;
        this.hospitalInfo_open = hospitalInfo_open;
        this.hospitalInfo_close = hospitalInfo_close;
        this.hospitalInfo_url = hospitalInfo_url;
        this.hospitalInfo_rejected = hospitalInfo_rejected;
        this.hospitalInfo_category = hospitalInfo_category;
        this.hospitalProfileBase64 = hospitalProfileBase64;
        this.hospitalProfileType = hospitalProfileType;
    }
}