package com.ssafy.lam.file.controller;

import com.ssafy.lam.file.service.UploadFileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@RequestMapping("/api/image")
@RequestMapping("/api/file")
public class UploadFileController {

    private UploadFileService imageService;

    @Autowired
    public UploadFileController(UploadFileService imageService) {
        this.imageService = imageService;
    }

//    @PostMapping("/upload")
    @PostMapping("/regist")
    @Operation(summary = "사진 등록")
    public ResponseEntity<String> uploadFile(@ModelAttribute MultipartFile imageFile) {
        imageService.store(imageFile, null, null);
        return new ResponseEntity<>(imageFile.toString(), HttpStatus.OK);
    }

    @GetMapping("/fole/{fileSeq}")
    @Operation(summary = "사진 조회")
    public ResponseEntity<Resource> getFile(@PathVariable Long fileSeq) {
        Resource file = imageService.loadFile(fileSeq);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
