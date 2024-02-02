package com.ssafy.lam.freeboard;

import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.dto.FreeboardRequestDto;
import com.ssafy.lam.freeboard.dto.FreeboardResponseDto;
import com.ssafy.lam.freeboard.service.FreeboardService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class FreeboardServiceTest {
    @Autowired
    private FreeboardService freeboardService;

    @Test
    @DisplayName("자유게시판 글 등록 테스트")
//    @Transactional
    public void registTest() {
        FreeboardRequestDto freeboardRequestDto = FreeboardRequestDto.builder()
                .freeBoard_seq(1L)
                .user_seq(1L)
                .freeBoard_title("자유게시판 테스트")
                .username("kimheesu")
                .userId("polya")
                .freeBoard_content("자유게시판 테스트 내용")
                .build();

        Freeboard freeboard = freeboardService.createFreeboard(freeboardRequestDto);
        Assertions.assertThat(freeboard.getTitle()).isEqualTo(freeboardRequestDto.getFreeBoard_title());

    }

    @Test
    @DisplayName("자유게시판 글 목록 조회 테스트")
    @Transactional
    public void getFreeboardListTest() {
        FreeboardRequestDto freeboardRequestDto = FreeboardRequestDto.builder()
                .user_seq(1L)
                .freeBoard_title("자유게시판 테스트")
                .userId("polya")
                .freeBoard_content("자유게시판 테스트 내용")
                .build();
//
//        Freeboard freeboard = freeboardService.createFreeboard(freeboardRequestDto);


        // 여기서 왜 insert를 하려고함?
        List<Freeboard> freeboardList = freeboardService.getAllFreeboards();
        System.out.println("freeboardList = " + freeboardList);

        List<FreeboardResponseDto> freeboardResponseDtoList = new ArrayList<>();

        for(Freeboard board : freeboardList){
            FreeboardResponseDto freeboardResponseDto = FreeboardResponseDto.builder()
                    .freeboardSeq(board.getFreeboardSeq())
                    .userId(board.getUser().getUserId())
                    .freeboardTitle(board.getTitle())
                    .freeboardRegisterdate(board.getRegisterDate())
                    .build();
            freeboardResponseDtoList.add(freeboardResponseDto);
        }

        System.out.println("freeboardResponseDtoList = " + freeboardResponseDtoList);
    }

    @Test
    @DisplayName("자유게시판 글 상세보기 테스트")
    @Transactional
    public void detailTest(){
        Long freeBoardSeq = 1L;
        Freeboard freeboard = freeboardService.getFreeboard(freeBoardSeq);
        Assertions.assertThat(freeboard.getFreeboardSeq()).isEqualTo(freeBoardSeq);

    }

    @Test
    @DisplayName("자유게시판 글 수정 테스트")
    public void updateTest(){
        Long user_eq = 1L;
        FreeboardRequestDto freeboardRequestDto = FreeboardRequestDto.builder()
                .freeBoard_seq(2L)
                .freeBoard_title("자유게시판 수정 테스트")
                .freeBoard_content("자유게시판 테스트 수정 내용")
                .build();

        Freeboard freeboard = freeboardService.updateFreeboard(user_eq, freeboardRequestDto);
        Assertions.assertThat(freeboard.getTitle()).isEqualTo(freeboardRequestDto.getFreeBoard_title());
    }
}
