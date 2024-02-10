package com.ssafy.lam.hospital.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HospitalAdminDto {
    private Long hospitalSeq;
    private Long userSeq;
    private String hospitalInfo_id;
    private String hospitalInfo_name;
    private boolean isApproved;
    private String profileBase64;
    private String registrationFileBase64;

    @Builder
    public HospitalAdminDto(Long hospitalSeq, Long userSeq, String hospitalInfo_id, String hospitalInfo_name, boolean isApproved, String profileBase64, String registrationFileBase64) {
        this.hospitalSeq = hospitalSeq;
        this.userSeq = userSeq;
        this.hospitalInfo_id = hospitalInfo_id;
        this.hospitalInfo_name = hospitalInfo_name;
        this.isApproved = isApproved;
        this.profileBase64 = profileBase64;
        this.registrationFileBase64 = registrationFileBase64;
    }
}
