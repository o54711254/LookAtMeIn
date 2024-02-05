package com.ssafy.lam.file.service;

import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.dto.FileRequestDto;
import com.ssafy.lam.file.dto.FileResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {

    UploadFile store(MultipartFile multipartFile, String origial, String after);
    
    // 사진 업로드
    FileRequestDto saveFile(MultipartFile file) throws IOException;

    // 사진 다운로드
//    ImageDownloadDto downloadImage(ImageRequestDto imageRequestDto);
    FileResponseDto getUploadFile(Long fileSeq);
}
