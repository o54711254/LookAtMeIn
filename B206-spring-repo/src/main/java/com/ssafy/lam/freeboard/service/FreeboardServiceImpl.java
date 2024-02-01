package com.ssafy.lam.freeboard.service;

import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.domain.FreeboardRepository;
import com.ssafy.lam.freeboard.dto.FreeboardDto;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeboardServiceImpl implements FreeboardService {
    private final FreeboardRepository freeboardRepository;
    private final UserRepository userRepository;



    @Override
    public Freeboard createFreeboard(FreeboardDto freeboardDto) {

        User user = userRepository.findById(freeboardDto.getUserSeq()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        Freeboard freeboard = Freeboard.builder()
                .user(user)
                .title(freeboardDto.getFreeBoard_title())
                .content(freeboardDto.getFreeBoard_content())
                .build();

        return freeboardRepository.save(freeboard);
    }

    @Override
    public List<Freeboard> getAllFreeboards() {
        List<Freeboard> freeboardList = freeboardRepository.findAll();

        return freeboardList;
    }

    @Override
    public Freeboard getFreeboard(Long freeBoardSeq) {
        return null;
    }

    @Override
    public Freeboard updateFreeboard(Long freeBoardSeq, Freeboard updatedFreeboard) {
        return null;
    }

    @Override
    public Freeboard getFreeboardDetail(Long freeBoardSeq) {
        Freeboard freeboard = freeboardRepository.findById(freeBoardSeq).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        return freeboard;
    }
}
