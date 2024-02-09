package com.ssafy.lam.requestboard.controller;

import com.ssafy.lam.requestboard.dto.RequestDto;
import com.ssafy.lam.requestboard.dto.RequestSaveDto;
import com.ssafy.lam.requestboard.service.RequestBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //
    @GetMapping("/read")
    public ResponseEntity<?> getAllRequestboard() {
        List<RequestDto> list = requestboardService.findAllRequestbooard();
        return new ResponseEntity<List<RequestDto>>(list, HttpStatus.OK);
    }

//    @GetMapping("/detail/{requestSeq}")
//    public ResponseEntity<?> getRequestBySeq(@PathVariable Long requestSeq) {
//
//    }
}
