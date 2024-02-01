package com.ssafy.lam.freeboard.service;

import com.ssafy.lam.freeboard.domain.Freeboard;

import java.util.List;

public interface FreeboardService {
    Freeboard createFreeboard(Freeboard freeboard);
    List<Freeboard> getAllFreeboards();

    Freeboard getFreeboard(Long freeBoardSeq);


    Freeboard updateFreeboard(Long freeBoardSeq, Freeboard updatedFreeboard);

    void
}
