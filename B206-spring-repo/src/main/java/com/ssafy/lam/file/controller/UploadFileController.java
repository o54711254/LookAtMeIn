package com.ssafy.lam.file.controller;

import com.ssafy.lam.file.service.UploadFileService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

@RestController
//@RequestMapping("/api/image")
@RequestMapping("/api/file")
public class UploadFileController {

    private UploadFileService fileService;
    private Logger log = LoggerFactory.getLogger(UploadFileController.class);

    @Autowired
    public UploadFileController(UploadFileService fileService) {
        this.fileService = fileService;
    }

//    @PostMapping("/upload")
    @PostMapping("/regist")
    @Operation(summary = "사진 등록")
    public ResponseEntity<String> uploadFile(@ModelAttribute MultipartFile imageFile) {
        fileService.store(imageFile);
        return new ResponseEntity<>(imageFile.toString(), HttpStatus.OK);
    }

    @GetMapping("/{fileSeq}")
    @Operation(summary = "사진 조회")
    public ResponseEntity<?> getFile(@PathVariable Long fileSeq) {
        try{
            File file = fileService.loadFile(fileSeq);
            String str = URLEncoder.encode(file.getAbsolutePath(), "UTF-8").replace("+", "%20");

//            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//            System.out.println("resource = " + resource.getURI());

            Resource resource = new UrlResource(file.toURI());
            log.info("resource = " + resource);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + str + "\"")
                    .body(resource);

        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }


    }

}
