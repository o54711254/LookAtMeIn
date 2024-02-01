package com.ssafy.lam.reserve.service;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.domain.HospitalRepository;
import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.domain.ReserveRepository;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.dto.ReserveSaveRequestDto;
import com.ssafy.lam.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReserveService {

    private final ReserveRepository reserveRepository;
    //    private final CustomerInfoRepository customerInfoRepository;
    private final CustomerRepository customerRepository;
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;

    //=================== INSERT ===================
    @Transactional
    public Reserve saveReserve(ReserveSaveRequestDto dto) {
//        CustomerInfo customerInfo = customerInfoRepository.findById(dto.getCustomerInfoSeq())
//                .orElseThrow(() -> new IllegalArgumentException("CustomerInfo not found. ID: " + dto.getCustomerInfoSeq()));
        Customer customerInfo = customerRepository.findByUserUserSeq(dto.getCustomerInfoSeq())
                .orElseThrow(() -> new IllegalArgumentException("CustomerInfo not found. ID: " + dto.getCustomerInfoSeq()));

//        System.out.println("dto.getHospitalInfoSeq()) ");

        Hospital hospital = hospitalRepository.findByUserUserSeq(dto.getHospitalInfoSeq())
                .orElseThrow(() -> new IllegalArgumentException("HosInfo not found. ID: " + dto.getHospitalInfoSeq()));


//        boolean isCustomer = userRepository.findById(customerInfo.getCustomerSeq());
        boolean isCustomer = customerInfo.getUser().getUserType().equals("CUSTOMER");
        boolean isHospital = hospital.getUser().getUserType().equals("HOSPITAL");

        if (isCustomer && isHospital) {
            Reserve reserve = dto.toEntity(customerInfo, hospital);
            return reserveRepository.save(reserve);
        } else {
            throw new IllegalArgumentException("User roles do not match the expected roles.");
        }

    }

    //=================== READ ===================
    public List<ReserveResponseDto> getReservesByUser(long userSeq) {
        List<Customer> customerInfos = customerRepository.findAllByUserUserSeq(userSeq);

        return customerInfos.stream()
                .flatMap(customerInfo -> reserveRepository.findAllByCustomerInfo(customerInfo).stream())
                .map(ReserveResponseDto::new)
                .collect(Collectors.toList());
    }

    //=================== DELETE ===================
    @Transactional
    public void deleteReserve(Long reserveSeq) {
        reserveRepository.findById(reserveSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다. reserveSeq=" + reserveSeq));

        reserveRepository.deleteById(reserveSeq);
    }
}