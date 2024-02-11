package com.ssafy.lam.reserve.service;

import com.ssafy.lam.reserve.domain.Reserve;
import com.ssafy.lam.reserve.dto.ReserveResponseDto;
import com.ssafy.lam.reserve.dto.ReserveSaveRequestDto;

import java.util.List;

public interface ReserveService {


    //=================== INSERT ===================
    Reserve saveReserve(ReserveSaveRequestDto dto);


    //=================== READ ===================
//    List<ReserveResponseDto> getReservesByUser(long userSeq);
//    List<ReserveResponseDto> findReservesByUserSeq(long userSeq);
    List<ReserveResponseDto> findByUserSeq(Long userSeq);

    //=================== DELETE ===================
    void deleteReserve(Long reserveSeq);
}