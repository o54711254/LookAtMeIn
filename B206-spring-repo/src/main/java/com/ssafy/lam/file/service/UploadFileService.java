package com.ssafy.lam.file.service;

import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.dto.FileUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {

    UploadFile store(MultipartFile multipartFile, String origial, String after);
    
    // 사진 업로드
    FileUploadDto saveFile(MultipartFile file) throws IOException;

    // 사진 다운로드
//    ImageDownloadDto downloadImage(ImageRequestDto imageRequestDto);
    
}
