package com.ssafy.lam.freeboard.controller;

import com.ssafy.lam.exception.NoArticleExeption;
import com.ssafy.lam.exception.UnAuthorizedException;
import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.dto.FreeboardRequestDto;
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
    public ResponseEntity<Void> regist(@RequestBody FreeboardRequestDto freeBoardRequestDto) {
        log.info("글 등록 정보 : {}", freeBoardRequestDto);
        freeboardService.createFreeboard(freeBoardRequestDto);
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
    public ResponseEntity<?> detail(@PathVariable Long freeBoard_seq){
        Freeboard freeboard =  null ;
        try{
            freeboard = freeboardService.getFreeboard(freeBoard_seq);
            FreeboardResponseDto freeboardResponseDto = FreeboardResponseDto.builder()
                    .freeboardSeq(freeboard.getFreeboardSeq())
                    .userId(freeboard.getUser().getUserId())
                    .freeboardTitle(freeboard.getTitle())
                    .freeboardContent(freeboard.getContent())
                    .freeboardRegisterdate(freeboard.getRegisterDate())
                    .build();

            return ResponseEntity.ok().body(freeboardResponseDto);
        }catch(NoArticleExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }




    }
    @PutMapping("/update/{user_seq}")
    @Operation(summary = "자유게시판 글 수정")
    public ResponseEntity<?> update(@PathVariable Long user_seq, @RequestBody FreeboardRequestDto freeboardRequestDto){
        try{
            freeboardService.updateFreeboard(user_seq, freeboardRequestDto);
            return ResponseEntity.ok().build();
        }catch (NoArticleExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(UnAuthorizedException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/delete/{freeBoard_seq}")
    @Operation(summary = "자유게시판 글 삭제")
    public ResponseEntity<?> delete(@PathVariable Long freeBoard_seq){
        try{
            freeboardService.deleteFreeboard(freeBoard_seq);
            return ResponseEntity.ok().build();
        }catch (NoArticleExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
