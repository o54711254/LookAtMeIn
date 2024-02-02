package com.ssafy.lam.freeboard.service;

import com.ssafy.lam.exception.NoArticleExeption;
import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.domain.FreeboardRepository;
import com.ssafy.lam.freeboard.dto.FreeboardRequestDto;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeboardServiceImpl implements FreeboardService {
    private final FreeboardRepository freeboardRepository;
    private final UserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(FreeboardServiceImpl.class);


    @Override
    public Freeboard createFreeboard(FreeboardRequestDto freeboardRequestDto) {

        User user = userRepository.findById(freeboardRequestDto.getUser_seq()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        log.info("글 등록 유저 정보: {}", user);
        Freeboard freeboard = Freeboard.builder()
                .user(user)
                .title(freeboardRequestDto.getFreeBoard_title())
                .content(freeboardRequestDto.getFreeBoard_content())
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
        Freeboard freeboard = freeboardRepository.findById(freeBoardSeq).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        return freeboard;
    }

    @Override
    public Freeboard updateFreeboard(Long user_seq, FreeboardRequestDto updateFreeboardRequestDto) {
        Freeboard freeboard = freeboardRepository.findById(updateFreeboardRequestDto.getFreeBoard_seq())
                .orElseThrow(() -> new NoArticleExeption("해당 게시글이 없습니다."));

        if(freeboard.getUser().getUserSeq() != user_seq){
            throw new IllegalArgumentException("해당 게시글을 수정할 권한이 없습니다.");
        }

        Freeboard updatedFreeboard = Freeboard.builder()
                .freeboardSeq(freeboard.getFreeboardSeq())
                .title(updateFreeboardRequestDto.getFreeBoard_title())
                .content(updateFreeboardRequestDto.getFreeBoard_content())
                .build();


        return freeboardRepository.save(updatedFreeboard);
    }


    @Override
    public void deleteFreeboard(Long freeBoardSeq) throws NoArticleExeption {

        Freeboard freeboard = freeboardRepository.findById(freeBoardSeq).orElseThrow(() -> new NoArticleExeption("해당 게시글이 없습니다."));
        freeboard.setDeleted(true);
        freeboardRepository.save(freeboard);


    }
}
