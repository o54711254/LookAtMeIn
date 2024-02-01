package com.ssafy.lam.freeboard.controller;

import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.dto.FreeboardDto;
import com.ssafy.lam.freeboard.dto.FreeboardResponseDto;
import com.ssafy.lam.freeboard.service.FreeboardService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/freeBoard")
public class FreeboardController {
    private final FreeboardService freeboardService;

    private Logger log = LoggerFactory.getLogger(FreeboardController.class);

    @Autowired
    public FreeboardController(FreeboardService freeboardService) {
        log.info("FreeBoardController init");
        this.freeboardService = freeboardService;
    }

    @PostMapping("/regist")
    @Operation(summary = "자유게시판 글 등록")
    public ResponseEntity<Void> regist(@RequestBody FreeboardDto freeBoardDto) {
        log.info("글 등록 정보 : {}", freeBoardDto);
        freeboardService.createFreeboard(freeBoardDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/freeBoardList")
    @Operation(summary = "자유게시판 글 목록")
    public ResponseEntity<List<FreeboardResponseDto>> getFreeboardList() {
        List<Freeboard> freeboardList = freeboardService.getAllFreeboards();
        List<FreeboardResponseDto> freeboardResponseDtoList = new ArrayList<>();

        for(Freeboard freeboard : freeboardList){
            FreeboardResponseDto freeboardResponseDto = FreeboardResponseDto.builder()
                    .freeboardSeq(freeboard.getFreeboardSeq())
                    .userId(freeboard.getUser().getUserId())
                    .freeboardTitle(freeboard.getTitle())
                    .freeboardRegisterdate(freeboard.getRegisterDate())
                    .build();
            freeboardResponseDtoList.add(freeboardResponseDto);
        }


        return ResponseEntity.ok().body(freeboardResponseDtoList);
    }

    @GetMapping("/freeBoardList/{freeBoard_seq}")
    @Operation(summary = "자유게시판 글 상세보기")
    public ResponseEntity<FreeboardResponseDto> detail(@PathVariable Long freeBoard_seq){
        Freeboard freeboard = freeboardService.getFreeboardDetail(freeBoard_seq);

        FreeboardResponseDto freeboardResponseDto = FreeboardResponseDto.builder()
                .freeboardSeq(freeboard.getFreeboardSeq())
                .userId(freeboard.getUser().getUserId())
                .freeboardTitle(freeboard.getTitle())
                .freeboardRegisterdate(freeboard.getRegisterDate())
                .build();

        return ResponseEntity.ok().body(freeboardResponseDto);

    }
}
