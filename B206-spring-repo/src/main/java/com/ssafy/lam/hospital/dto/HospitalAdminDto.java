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

    private String registrationFileBase64;
    private String registrationFileType;

    @Builder
    public HospitalAdminDto(Long hospitalSeq, Long userSeq, String hospitalInfo_id, String hospitalInfo_name, boolean isApproved, String registrationFileBase64, String registrationFileType) {
        this.hospitalSeq = hospitalSeq;
        this.userSeq = userSeq;
        this.hospitalInfo_id = hospitalInfo_id;
        this.hospitalInfo_name = hospitalInfo_name;
        this.isApproved = isApproved;
        this.registrationFileBase64 = registrationFileBase64;
        this.registrationFileType = registrationFileType;
    }
}
