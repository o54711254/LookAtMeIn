package com.ssafy.lam.requestboard.controller;

import com.ssafy.lam.chat.dto.ChatRoomResponseDto;
import com.ssafy.lam.chat.service.ChatService;
import com.ssafy.lam.requestboard.dto.RequestDto;
import com.ssafy.lam.requestboard.dto.RequestSaveDto;
import com.ssafy.lam.requestboard.dto.RequestUpdateDto;
import com.ssafy.lam.requestboard.dto.ResponseDto;
import com.ssafy.lam.requestboard.service.RequestBoardService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final ChatService chatService;


    @PostMapping("/register")
    @Operation(summary = "게시판 등록")
    public ResponseEntity<Long> registerRequest(@RequestBody RequestSaveDto requestSaveDto) {
        Long savedRequestId = requestboardService.saveRequestboard(requestSaveDto);
        return ResponseEntity.ok(savedRequestId);
    }

    //
    @GetMapping("/read")
    @Operation(summary = "게시물 전체조회")
    public ResponseEntity<?> getAllRequestboard() {
        List<RequestDto> list = requestboardService.findAllRequestbooard();
        return new ResponseEntity<List<RequestDto>>(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{requestSeq}")
    @Operation(summary = "게시물 상세조회")
    public ResponseEntity<?> getRequestBySeq(@PathVariable Long requestSeq) {
        RequestDto requestDto = requestboardService.finRequestboard(requestSeq);
        return new ResponseEntity<RequestDto>(requestDto, HttpStatus.OK);
    }

    @PutMapping("/delete/{requestSeq}")
    @Operation(summary = "게시물 삭제")
    public ResponseEntity<?> deleteRequestboard(@PathVariable Long requestSeq) {
        requestboardService.deleteRequestboard(requestSeq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{requestSeq}")
    @Operation(summary = "게시물 수정")
    public ResponseEntity<RequestUpdateDto> updateRequestboard(@PathVariable Long requestSeq, @RequestBody RequestUpdateDto requestUpdateDto) {
        RequestUpdateDto updatedRequestDto = requestboardService.updateRequestboard(requestSeq, requestUpdateDto);
        return ResponseEntity.ok(updatedRequestDto);
    }

    @PostMapping("/response/{requestSeq}")
    @Operation(summary = "게시물 올린 고객에게 제안하기")
    public ResponseEntity<Void> createResponse(@PathVariable Long requestSeq, @RequestBody ResponseDto responseDto) {
        requestboardService.createResponse(requestSeq, responseDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept-response/{responseId}")
    @Operation(summary = "제안에 대한 수락")
    public ResponseEntity<ChatRoomResponseDto> acceptResponse(@PathVariable Long responseId) {
        ChatRoomResponseDto chatRoomResponseDto = chatService.acceptAndCreateChatRoom(responseId);
        return ResponseEntity.ok(chatRoomResponseDto);
    }


}
