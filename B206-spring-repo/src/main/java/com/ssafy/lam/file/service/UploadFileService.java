package com.ssafy.lam.file.service;

import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.dto.FileResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UploadFileService {

    UploadFile store(MultipartFile multipartFile);

    UploadFile getUploadFile(Long fileSeq);

}
