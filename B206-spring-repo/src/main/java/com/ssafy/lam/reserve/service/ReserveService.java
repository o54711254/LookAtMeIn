package com.ssafy.lam.reserve.service;

import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.domain.ReserveRepository;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.dto.ReserveSaveRequestDto;
import com.ssafy.lam.coordinator.domain.Coordinator;
import com.ssafy.lam.coordinator.domain.CoordinatorRepository;
//import com.ssafy.lam.user.domain.CustomerInfoRepository;
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
    private final CoordinatorRepository coordinatorRepository;

    //=================== INSERT ===================
    @Transactional
    public Reserve saveReserve(ReserveSaveRequestDto dto) {
//        CustomerInfo customerInfo = customerInfoRepository.findById(dto.getCustomerInfoSeq())
//                .orElseThrow(() -> new IllegalArgumentException("CustomerInfo not found. ID: " + dto.getCustomerInfoSeq()));
        Customer customerInfo = customerRepository.findById(dto.getCustomerInfoSeq())
                .orElseThrow(() -> new IllegalArgumentException("CustomerInfo not found. ID: " + dto.getCustomerInfoSeq()));

        Coordinator coordinator = coordinatorRepository.findById(dto.getCoordInfoSeq())
                .orElseThrow(() -> new IllegalArgumentException("CoordInfo not found. ID: " + dto.getCoordInfoSeq()));

        Reserve reserve = dto.toEntity(customerInfo, coordinator);
        return reserveRepository.save(reserve);
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