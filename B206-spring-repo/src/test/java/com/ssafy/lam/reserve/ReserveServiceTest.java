package com.ssafy.lam.reserve;

import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.domain.HospitalRepository;
import com.ssafy.lam.reserve.dto.ReserveSaveRequestDto;
import com.ssafy.lam.reserve.service.ReserveService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest

public class ReserveServiceTest {
    @Autowired
//    private ReserveService reserveService;

    private HospitalRepository hospitalRepository;
    @Test
    @DisplayName("reserveTest")
    public void test() {
        ReserveSaveRequestDto reserveSaveRequestDto = new ReserveSaveRequestDto();
        reserveSaveRequestDto.setCustomerInfoSeq(1L);
        reserveSaveRequestDto.setHospitalInfoSeq(2L);


        Hospital hospital = hospitalRepository.findByUserUserSeq(reserveSaveRequestDto.getHospitalInfoSeq()).orElse(null);

        System.out.println("hospital = " + hospital);

    }

}
