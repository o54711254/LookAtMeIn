package com.ssafy.lam.reserve.service;

import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.hospital.domain.HospitalRepository;
import com.ssafy.lam.reserve.domain.PastReserveRepository;
import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.domain.ReserveRepository;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.dto.ReserveSaveRequestDto;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {

    private final ReserveRepository reserveRepository;
    private final CustomerRepository customerRepository;
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final PastReserveRepository pastReserveRepository;

    @Override
    public List<ReserveResponseDto> findByUserSeq(Long userSeq) {
        return reserveRepository.findByUserSeq(userSeq).stream()
                .map(reserve -> new ReserveResponseDto(
                        reserve.getCustomer().getName(),
                        reserve.getHospital().getName(),
                        reserve.getReserveType(),
                        reserve.getYear(),
                        reserve.getMonth(),
                        reserve.getDay(),
                        reserve.getDayofweek(),
                        reserve.getTime()))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Reserve saveReserve(ReserveSaveRequestDto dto) {
        User customerUser = userRepository.findById(dto.getCustomerUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("없는 유저임 : " + dto.getCustomerUserSeq()));

        User hospitalUser = userRepository.findById(dto.getHospitalUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("없는 유저임 : " + dto.getHospitalUserSeq()));

        if (customerUser.getUserType().equals("CUSTOMER") && hospitalUser.getUserType().equals("HOSPITAL")) {
            Reserve reserve = dto.toEntity(customerUser, hospitalUser);
            return reserveRepository.save(reserve);
        } else {
            throw new IllegalArgumentException("고객과 병원 매치가 안됨");
        }
    }


    @Override
    @Transactional
    public void deleteReserve(Long reserveSeq) {
        reserveRepository.findById(reserveSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다. reserveSeq=" + reserveSeq));

        reserveRepository.deleteById(reserveSeq);
    }

}