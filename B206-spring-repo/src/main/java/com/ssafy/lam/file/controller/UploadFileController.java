package com.ssafy.lam.file.controller;

import com.ssafy.lam.common.EncodeFile;
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


    // 앞서서 상세조회할 때 반환한 URL로 이미지를 불러옴
    @GetMapping("/{fileSeq}")
    @Operation(summary = "사진 조회")
    public ResponseEntity<FileResponseDto> getFile(@PathVariable Long fileSeq) {
        try{
            UploadFile uploadFile = fileService.getUploadFile(fileSeq); // fileSeq에 맞는 파일을 DB에서 조회해서 반환
            Path path = Paths.get(uploadPath+"/"+uploadFile.getName()); // 파일이 저장된 경로를 가져옴

            // 파일을 base64로 인코딩해서 보내줘야함
            // 프론트에서 파일을 볼 때는 항상 base64로 인코딩해서 보내야함
            String encodeFile = EncodeFile.encodeFileToBase64(path);
            Resource resource = new UrlResource(path.toUri());

            if(resource.exists() || resource.isReadable()){

                // 파일 정보를 담아서 반환4
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

}
