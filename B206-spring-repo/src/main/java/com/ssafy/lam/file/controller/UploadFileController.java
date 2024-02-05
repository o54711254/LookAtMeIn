package com.ssafy.lam.file.controller;

import com.ssafy.lam.file.service.UploadFileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@RequestMapping("/api/image")
@RequestMapping("/api/questionnaire")
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

//    @PostMapping("/download")
//    public ResponseEntity<ImageDownloadDto> downloadImage() {
//        ImageDownloadDto imageDownloadDto = new ImageDownloadDto();
//        return ResponseEntity.ok().body(imageDownloadDto);
//    }

}
