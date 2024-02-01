package com.ssafy.lam.freeboard.controller;

import com.ssafy.lam.freeboard.dto.FreeboardDto;
import com.ssafy.lam.freeboard.service.FreeboardService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/freeBoard")
public class FreeboardController {
    private final FreeboardService freeBoardService;

    private Logger log = LoggerFactory.getLogger(FreeboardController.class);

    @Autowired
    public FreeboardController(FreeboardService freeBoardService) {
        log.info("FreeBoardController init");
        this.freeBoardService = freeBoardService;
    }

    @PostMapping("/regist")
    @Operation(summary = "자유게시판 글 등록")
    public ResponseEntity<Void> regist(@RequestBody FreeboardDto freeBoardDto) {
        log.info("글 등록 정보 : {}", freeBoardDto);
        freeBoardService.createFreeBoard(freeBoardDto);
        return ResponseEntity.ok().build();
    }
}
