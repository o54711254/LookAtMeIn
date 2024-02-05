package com.ssafy.lam.file.service;

import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.domain.UploadFileRepository;
import com.ssafy.lam.file.dto.FileUploadDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {

    private final UploadFileRepository uploadFileRepository;

//    @Value("${upload.path}")
//    private String uploadPath;
    MultipartConfig multipartConfig = new MultipartConfig();
    // 파일 저장 위치: c:\lamImages - MultipartConfig.java에서 설정
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

    private Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    @Override
    public UploadFile store(MultipartFile file, String original, String after) {
        FileUploadDto fileUploadDto = saveFile(file);
        if (fileUploadDto != null) {
            UploadFile uploadFile = UploadFile.builder()
                    .originalPath(fileUploadDto.getOriginName()).build();
            return uploadFileRepository.save(uploadFile);
        }
        return null;
    }

    // 파일 업로드
    @Override
    public FileUploadDto saveFile(MultipartFile file) {
        FileUploadDto fileUploadDto = null;
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
                File localFile = new File(saveName);
                file.transferTo(localFile);
                fileUploadDto = FileUploadDto.builder()
                        .originName(saveName)
                        .build();
                log.info("파일 저장 완료: {}", localFile.getCanonicalPath());
            } catch (IllegalStateException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return fileUploadDto;
    }

//    @Override
//    public ImageDownloadDto downloadImage(ImageRequestDto imageRequestDto) {
//        return null;
//    }
}
