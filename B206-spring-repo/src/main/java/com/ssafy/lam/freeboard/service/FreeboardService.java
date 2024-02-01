package com.ssafy.lam.freeboard.service;

import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.dto.FreeboardDto;

import java.util.List;

public interface FreeboardService {
    Freeboard createFreeboard(FreeboardDto freeboardDto);
    List<Freeboard> getAllFreeboards();

    Freeboard getFreeboard(Long freeBoardSeq);


    Freeboard updateFreeboard(Long freeBoardSeq, Freeboard updatedFreeboard);
    Freeboard getFreeboardDetail(Long freeBoardSeq);

}
