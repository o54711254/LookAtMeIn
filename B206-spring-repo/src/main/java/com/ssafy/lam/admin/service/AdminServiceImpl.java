package com.ssafy.lam.admin.service;

import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.domain.FreeboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final FreeboardRepository freeboardRepository;

    @Override
    public List<Freeboard> findComplainedFreeboards() {
        return freeboardRepository.findByComplainTrue();
    }
}
