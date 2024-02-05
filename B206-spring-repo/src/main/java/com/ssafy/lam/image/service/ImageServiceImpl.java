package com.ssafy.lam.image.service;

import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.image.domain.Image;
import com.ssafy.lam.image.domain.ImageRepository;
import com.ssafy.lam.image.dto.ImageUploadDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

//    @Value("${upload.path}")
//    private String uploadPath;
    MultipartConfig multipartConfig = new MultipartConfig();
    // 파일 저장 위치: c:\lamImages - MultipartConfig.java에서 설정
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

    private Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public void store(MultipartFile file) {
        ImageUploadDto imageUploadDto = saveImage(file);
        if (imageUploadDto != null) {
            Image image = Image.builder().imageSeq(imageUploadDto.getSeq()).realName(imageUploadDto.getOriginName())
                    .sysName(imageUploadDto.getSaveName()).build();
            imageRepository.save(image);
        }
    }

    // 파일 업로드
    @Override
    public ImageUploadDto saveImage(MultipartFile file) {
        ImageUploadDto imageUploadDto = null;
        if (file != null) {
            String originName = file.getOriginalFilename();
            String saveName = UUID.randomUUID() + "_" + originName;

            try {
                // 파일 업로드
                log.info("file path : {}", uploadPath);
                File localFile = new File(uploadPath + "/" + saveName);
                file.transferTo(localFile);
                imageUploadDto = ImageUploadDto.builder()
                        .originName(originName)
                        .saveName(saveName)
                        .build();
                log.info("파일 저장 완료: {}", localFile.getCanonicalPath());
            } catch (IllegalStateException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imageUploadDto;
    }

//    @Override
//    public ImageDownloadDto downloadImage(ImageRequestDto imageRequestDto) {
//        return null;
//    }
}
