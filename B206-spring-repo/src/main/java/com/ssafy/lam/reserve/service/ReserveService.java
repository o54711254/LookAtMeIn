package com.ssafy.lam.reserve.service;

import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.domain.ReserveRepository;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.dto.ReserveSaveRequestDto;
import com.ssafy.lam.reserve.dto.ReserveUpdateRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReserveService {

    private final ReserveRepository reserveRepository;

    @Transactional
    public Long save(ReserveSaveRequestDto reserveDto) {
        return reserveRepository.save(reserveDto.toEntity()).getReserveSeq();
    }

    public ReserveResponseDto findById(long coordinatorSeq) {
        Reserve entity = reserveRepository.findById(coordinatorSeq).orElseThrow();
        return new ReserveResponseDto(entity);
    }

    @Transactional
    public List<Reserve> findAllById(long coordinatorSeq) {
        List<Reserve> reserves = reserveRepository.findAllById(coordinatorSeq);
        return reserves;
    }



    @Transactional
    public void delete(Long reserveSeq) {
        Reserve reserve = reserveRepository.findById(reserveSeq).orElseThrow();
        reserveRepository.delete(reserve);
    }
}
