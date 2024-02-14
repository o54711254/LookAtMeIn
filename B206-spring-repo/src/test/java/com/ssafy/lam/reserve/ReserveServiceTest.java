package com.ssafy.lam.reserve;

import com.ssafy.lam.hospital.domain.HospitalRepository;
import com.ssafy.lam.reserve.dto.ReserveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class ReserveServiceTest {
    @Autowired
//    private ReserveService reserveService;

    private HospitalRepository hospitalRepository;
    @Test
    @DisplayName("reserveTest")
    public void test() {
        ReserveRequestDto reserveRequestDto = new ReserveRequestDto();
//        reserveSaveRequestDto.setCustomerInfoSeq(1L);
//        reserveSaveRequestDto.setHospitalInfoSeq(2L);


//        Hospital hospital = hospitalRepository.findByUserUserSeq(reserveSaveRequestDto.getHospitalInfoSeq()).orElse(null);

//        System.out.println("hospital = " + hospital);

    }

}
