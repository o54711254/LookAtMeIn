package com.ssafy.lam.freeboard.service;

import com.ssafy.lam.exception.NoArticleExeption;
import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.dto.FreeboardRequestDto;

import java.util.List;

public interface FreeboardService {
    Freeboard createFreeboard(FreeboardRequestDto freeboardRequestDto);
    List<Freeboard> getAllFreeboards();

    Freeboard getFreeboard(Long freeBoardSeq);


    Freeboard updateFreeboard(Long user_seq, FreeboardRequestDto updatedFreeboard);


    Freeboard deleteFreeboard(Long freeBoardSeq) throws NoArticleExeption;

}
