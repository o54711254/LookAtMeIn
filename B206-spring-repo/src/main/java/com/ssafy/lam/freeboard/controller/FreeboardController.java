package com.ssafy.lam.freeboard.controller;

import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.exception.NoArticleExeption;
import com.ssafy.lam.exception.UnAuthorizedException;

import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.service.UploadFileService;
import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.dto.FreeboardRequestDto;
import com.ssafy.lam.freeboard.dto.FreeboardResponseDto;
import com.ssafy.lam.freeboard.service.FreeboardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/freeBoard")
@RequiredArgsConstructor
public class FreeboardController {
    private final FreeboardService freeboardService;
    private final UploadFileService uploadFileService;
    private final CustomerRepository customerRepository;



    private Logger log = LoggerFactory.getLogger(FreeboardController.class);
    MultipartConfig multipartConfig = new MultipartConfig();
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

    @PostMapping("/regist")
    @Operation(summary = "자유게시판 글 등록")
    public ResponseEntity<Void> regist(@ModelAttribute FreeboardRequestDto freeBoardRequestDto) {
        log.info("글 등록 정보 : {}", freeBoardRequestDto);
        System.out.println(freeBoardRequestDto);
        Freeboard freeboard = freeboardService.createFreeboard(freeBoardRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/freeBoardList")
    @Operation(summary = "자유게시판 글 목록")
    public ResponseEntity<List<FreeboardResponseDto>> getFreeboardList() {
        List<Freeboard> freeboardList = freeboardService.getAllFreeboards();
        List<FreeboardResponseDto> freeboardResponseDtoList = new ArrayList<>();

        for(Freeboard freeboard : freeboardList){
            Optional<Customer> customerOptional = customerRepository.findByUserUserSeq(freeboard.getUser().getUserSeq());
            Customer customer = null;
            if(customerOptional.isPresent()){
                customer = customerOptional.get();
                FreeboardResponseDto freeboardResponseDto = FreeboardResponseDto.builder()
                        .freeboardSeq(freeboard.getFreeboardSeq())
                        .userId(freeboard.getUser().getUserId())
                        .freeboardTitle(freeboard.getTitle())
                        .freeboardRegisterdate(freeboard.getRegisterDate())
                        .build();

                if(customer.getProfile() != null){
                    UploadFile customerProfile = customer.getProfile();
                    Path path = Paths.get(uploadPath+"/"+customerProfile.getName());
                    try{
                        String customerProfileBase64 = EncodeFile.encodeFileToBase64(path);
                        String customerProfileType = customerProfile.getType();
                        freeboardResponseDto.setCustomerProfileBase64(customerProfileBase64);
                        freeboardResponseDto.setCustomerProfileType(customerProfileType);
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                freeboardResponseDtoList.add(freeboardResponseDto);
            }
        }



        return ResponseEntity.ok().body(freeboardResponseDtoList);
    }

    @GetMapping("/freeBoardList/{freeBoard_seq}")
    @Operation(summary = "자유게시판 글 상세보기")
    public ResponseEntity<?> detail(@PathVariable Long freeBoard_seq){
        FreeboardResponseDto freeboardResponseDto =  null ;

        try{
            freeboardResponseDto = freeboardService.getFreeboard(freeBoard_seq);
//            uploadFileService.loadFile(freeboardResponseDto.getFileSeq());

            return ResponseEntity.ok().body(freeboardResponseDto);
        }catch(NoArticleExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }




    }
    @PutMapping("/update/{freeboardSeq}")
    @Operation(summary = "자유게시판 글 수정")
    public ResponseEntity<?> update(@PathVariable Long freeboardSeq, @RequestBody FreeboardRequestDto freeboardRequestDto){
        try{
            freeboardService.updateFreeboard(freeboardSeq, freeboardRequestDto);
            return ResponseEntity.ok().build();
        }catch (NoArticleExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(UnAuthorizedException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/delete/{freeBoard_seq}")
    @Operation(summary = "자유게시판 글 삭제")
    public ResponseEntity<?> delete(@PathVariable Long freeBoard_seq){
        try{
            freeboardService.deleteFreeboard(freeBoard_seq);
            return ResponseEntity.ok().build();
        }catch (NoArticleExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/report/{freeBoard_seq}")
    @Operation(summary = "자유게시판 글 신고")
    public ResponseEntity<?> report(@PathVariable Long freeBoard_seq) {
        boolean result = freeboardService.reportFreeboard(freeBoard_seq);
        if(result) {
            return ResponseEntity.ok().body("해당 게시글이 신고되었습니다. boardSeq: " + freeBoard_seq);
        }
        else {
            return ResponseEntity.badRequest().body("해당 게시글 신고에 실패하였습니다. boardSeq: " + freeBoard_seq);
        }

    }
}
