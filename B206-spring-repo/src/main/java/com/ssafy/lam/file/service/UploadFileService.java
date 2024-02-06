package com.ssafy.lam.file.service;

import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.dto.FileResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UploadFileService {

    UploadFile store(MultipartFile multipartFile);
    


    // 사진 다운로드
//    ImageDownloadDto downloadImage(ImageRequestDto imageRequestDto);
    FileResponseDto getUploadFile(Long fileSeq);

    File loadFile(Long fileSeq);
}
