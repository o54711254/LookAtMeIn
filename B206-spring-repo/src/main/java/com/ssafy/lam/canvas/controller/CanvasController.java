package com.ssafy.lam.canvas.controller;

import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.file.service.UploadFileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/canvas")
@RequiredArgsConstructor
public class CanvasController {
    private final UploadFileService uploadFileService;

    private Logger log = LoggerFactory.getLogger(CanvasController.class);

    MultipartConfig multipartConfig = new MultipartConfig();

    // 파일이 업로드될 디렉토리 경로
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

    @PostMapping("/save")
    @Operation(summary = "성형 전,후 사진 저장")
    public ResponseEntity<Void> save(@RequestParam("before")MultipartFile before, @RequestParam("after")MultipartFile after) {
//        uploadFileService.store(before);
        log.info("before = " + before);
        uploadFileService.store(before);
        uploadFileService.store(after);
        return ResponseEntity.ok().build();
    }
}
