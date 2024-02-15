package com.ssafy.lam.canvas.service;

import com.ssafy.lam.canvas.domain.Canvas;
import com.ssafy.lam.canvas.domain.CanvasRepository;
import com.ssafy.lam.canvas.dto.CanvasResponseDto;
import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.service.UploadFileService;
import com.ssafy.lam.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class CanvasService {
    private final CanvasRepository canvasRepository;
    private final UploadFileService uploadFileService;

    MultipartConfig multipartConfig = new MultipartConfig();

    // 파일이 업로드될 디렉토리 경로
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();
    private Logger log = LoggerFactory.getLogger(CanvasService.class);

    public void saveCanvas(Long userSeq, MultipartFile before, MultipartFile after) {

        // 1. userSeq로 이전에 저장한 canvas가 있는지 확인
        Canvas canvas = canvasRepository.findByCustomerUserSeq(userSeq);

        UploadFile beforeFile = uploadFileService.store(before);
        UploadFile afterFile = uploadFileService.store(after);
        // 2. 없으면 새로 저장
        if (canvas == null) {
            User user = User.builder().userSeq(userSeq).build();
            canvas = Canvas.builder()
                    .customer(user)
                    .before(beforeFile)
                    .after(afterFile)
                    .build();
        }else{
            // 3. 있으면 update
            canvas.setBefore(beforeFile);
            canvas.setAfter(afterFile);
        }

        canvasRepository.save(canvas);
    }

    public CanvasResponseDto getCanvasDetail(Long userSeq) {
        Canvas canvas = canvasRepository.findByCustomerUserSeq(userSeq);
        if (canvas == null) {
            return null;
        }
        CanvasResponseDto responseDto = new CanvasResponseDto();
        try{
            Path path = Paths.get(uploadPath+"/"+canvas.getBefore().getName());
            String beforeBase64 = EncodeFile.encodeFileToBase64(path);
            responseDto.setBeforeImgBase64(beforeBase64);
            path = Paths.get(uploadPath+"/"+canvas.getAfter().getName());
            String afterBase64 = EncodeFile.encodeFileToBase64(path);
            responseDto.setAfterImgBase64(afterBase64);
            return responseDto;
        }catch (Exception e){
            log.info("before file or after file not found");
            return null;
        }
    }

}
