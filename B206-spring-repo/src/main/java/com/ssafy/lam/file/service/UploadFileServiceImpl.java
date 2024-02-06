package com.ssafy.lam.file.service;

import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.domain.UploadFileRepository;
import com.ssafy.lam.file.dto.FileRequestDto;
import com.ssafy.lam.file.dto.FileResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {

    private final UploadFileRepository uploadFileRepository;

    MultipartConfig multipartConfig = new MultipartConfig();
    // 파일 저장 위치: c:\lamImages - MultipartConfig.java에서 설정
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

    private Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    @Override
    public UploadFile store(MultipartFile file) {
        UploadFile uploadFile = saveFile(file);
        return uploadFileRepository.save(uploadFile);
    }

    @Override
    public UploadFile getUploadFile(Long fileSeq){
        UploadFile uploadFile = uploadFileRepository.findById(fileSeq).orElse(null);
        if(uploadFile == null){
            return null;
        }

        return uploadFile;
    }


    // 파일 업로드
    public UploadFile saveFile(MultipartFile file) {
        UploadFile uploadFile = null;
        if (file != null) {
//            String originName = file.getOriginalFilename();
            StringTokenizer st = new StringTokenizer(file.getOriginalFilename(), ".");
            String originName = st.nextToken();
            String extension = st.nextToken();
            originName +="_"+UUID.randomUUID() +"."+extension;

            try {
                // 파일 업로드
                log.info("file path : {}", uploadPath);
                String saveName = uploadPath + "/" + originName;
//                String saveName = originName;
                File localFile = new File(saveName);
                file.transferTo(localFile);
                uploadFile = UploadFile.builder()
                        .name(originName)
                        .category(file.getContentType())
                        .build();
                log.info("파일 저장 완료: {}", localFile.getCanonicalPath());
            } catch (IllegalStateException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return uploadFile;
    }


}
