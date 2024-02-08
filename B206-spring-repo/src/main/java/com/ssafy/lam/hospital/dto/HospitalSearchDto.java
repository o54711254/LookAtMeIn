package com.ssafy.lam.hospital.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HospitalSearchDto {
    private String hospitalInfo_id;
    private String hospitalInfo_name;
    private String hospitalInfo_introduce;
    private String hospitalInfo_address;
    //해시
    //별점

    @Builder
    public HospitalSearchDto(String hospitalInfo_id, String hospitalInfo_name, String hospitalInfo_introduce, String hospitalInfo_address) {
        this.hospitalInfo_id = hospitalInfo_id;
        this.hospitalInfo_name = hospitalInfo_name;
        this.hospitalInfo_introduce = hospitalInfo_introduce;
        this.hospitalInfo_address = hospitalInfo_address;
    }
}
