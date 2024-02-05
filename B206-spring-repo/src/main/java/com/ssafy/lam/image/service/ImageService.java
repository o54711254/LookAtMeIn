package com.ssafy.lam.image.service;

import com.ssafy.lam.image.dto.ImageUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    void store(MultipartFile multipartFile);
    
    // 사진 업로드
    ImageUploadDto saveImage(MultipartFile file) throws IOException;

    // 사진 다운로드
//    ImageDownloadDto downloadImage(ImageRequestDto imageRequestDto);
    
}
