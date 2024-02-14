package com.ssafy.lam.file;

import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.dto.FileResponseDto;
import com.ssafy.lam.file.service.UploadFileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@SpringBootTest
public class FileServiceTest {
    @Autowired
    private UploadFileService uploadFileService;
    
    @Test
    @DisplayName("파일 조회 테스트")
    public void test() {
        // given
        Long fileSeq = 1L;
        UploadFile dto = uploadFileService.getUploadFile(fileSeq);
        System.out.println("dto.getOriginalPath().getFilename() = " + dto.getName());

//        Resource resource = uploadFileService.loadFile(fileSeq);
//        System.out.println("resource.getFilename() = " + resource.getFilename());
        // when
        // then
    }
}
