package com.ssafy.lam.canvas.controller;

import com.ssafy.lam.canvas.dto.CanvasResponseDto;
import com.ssafy.lam.canvas.service.CanvasService;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.file.service.UploadFileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/canvas")
@RequiredArgsConstructor
public class CanvasController {

    private final CanvasService canvasService;
    private Logger log = LoggerFactory.getLogger(CanvasController.class);



    @PostMapping("/save/{userSeq}")
    @Operation(summary = "성형 전,후 사진 저장")
    public ResponseEntity<Void> save(@PathVariable Long userSeq, @RequestParam("before")MultipartFile before, @RequestParam("after")MultipartFile after) {
//        uploadFileService.store(before);
        log.info("before = " + before);
//        uploadFileService.store(before);
//        uploadFileService.store(after);
        canvasService.saveCanvas(userSeq, before, after);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail/{userSeq}")
    @Operation(summary = "userSeq로 성형 전,후 사진을 가져옴")
    public ResponseEntity<CanvasResponseDto> getCanvasDetail(@PathVariable Long userSeq) {
        CanvasResponseDto canvasResponseDto = canvasService.getCanvasDetail(userSeq);
        if(canvasResponseDto == null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(canvasResponseDto);
        }
    }
}
