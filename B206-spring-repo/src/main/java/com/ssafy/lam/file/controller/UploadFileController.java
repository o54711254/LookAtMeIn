package com.ssafy.lam.file.controller;

import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.dto.FileResponseDto;
import com.ssafy.lam.file.service.UploadFileService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@RestController
//@RequestMapping("/api/image")
@RequestMapping("/api/file")
public class UploadFileController {

    private UploadFileService fileService;
    private Logger log = LoggerFactory.getLogger(UploadFileController.class);


    MultipartConfig multipartConfig = new MultipartConfig();
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

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
    public ResponseEntity<FileResponseDto> getFile(@PathVariable Long fileSeq) {
        try{
            UploadFile uploadFile = fileService.getUploadFile(fileSeq);
            Path path = Paths.get(uploadPath+"/"+uploadFile.getName());

            String encodeFile = encodeFileToBase64(path);
            Resource resource = new UrlResource(path.toUri());

            if(resource.exists() || resource.isReadable()){
                FileResponseDto fileReponseDto = FileResponseDto.builder()
                        .fileSeq(uploadFile.getSeq())
                        .name(uploadFile.getName())
                        .base64(encodeFile)
                        .type(uploadFile.getType())
                        .build();

                return ResponseEntity.ok(fileReponseDto);

            }else{
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    private String encodeFileToBase64(Path path) throws IOException {
        byte[] fileContent = Files.readAllBytes(path);
        return Base64.getEncoder().encodeToString(fileContent);
    }

}
