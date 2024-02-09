package com.ssafy.lam.requestboard.controller;

import com.ssafy.lam.requestboard.dto.RequestSaveDto;
import com.ssafy.lam.requestboard.service.RequestBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/requestboard")
public class RequestboardController {

    private final RequestBoardService requestboardService;


    @PostMapping("/register")
    public ResponseEntity<Long> registerRequest(@RequestBody RequestSaveDto requestSaveDto) {
        Long savedRequestId = requestboardService.saveRequestboard(requestSaveDto);
        return ResponseEntity.ok(savedRequestId);
    }


}
