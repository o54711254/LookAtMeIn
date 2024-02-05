package com.ssafy.lam.file;

import com.ssafy.lam.file.dto.FileResponseDto;
import com.ssafy.lam.file.service.UploadFileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileServiceTest {
    @Autowired
    private UploadFileService uploadFileService;
    
    @Test
    @DisplayName("파일 조회 테스트")
    public void test() {
        // given
        Long fileSeq = 1L;
        FileResponseDto dto = uploadFileService.getUploadFile(fileSeq);
        System.out.println("dto.getOriginalPath().getFilename() = " + dto.getOriginalPath().getFilename());
        // when
        // then
    }
}
