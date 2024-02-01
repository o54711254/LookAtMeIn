package com.ssafy.lam.freeboard.service;

import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.domain.FreeboardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FreeboardServiceImpl implements FreeboardService {
    private final FreeboardRepository freeboardRepository;
    @Autowired
    public FreeboardServiceImpl(FreeboardRepository freeboardRepository) {
        this.freeboardRepository = freeboardRepository;
    }


    @Override
    public Freeboard createFreeboard(Freeboard freeboard) {
        return null;
    }

    @Override
    public List<Freeboard> getAllFreeboards() {
        return null;
    }

    @Override
    public Freeboard getFreeboard(Long freeBoardSeq) {
        return null;
    }

    @Override
    public Freeboard updateFreeboard(Long freeBoardSeq, Freeboard updatedFreeboard) {
        return null;
    }
}
