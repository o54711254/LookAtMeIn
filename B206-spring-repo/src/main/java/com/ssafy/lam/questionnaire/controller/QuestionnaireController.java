package com.ssafy.lam.questionnaire.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.questionnaire.domain.Questionnaire;
import com.ssafy.lam.questionnaire.dto.QuestionnaireRequestDto;
import com.ssafy.lam.questionnaire.dto.QuestionnaireResponseDto;
import com.ssafy.lam.questionnaire.service.QuestionnaireService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@RestController
@RequestMapping("/api/questionnaire")
@RequiredArgsConstructor
public class QuestionnaireController {
    private Logger log = LoggerFactory.getLogger(QuestionnaireController.class);
    private final QuestionnaireService questionnaireService;

    MultipartConfig multipartConfig = new MultipartConfig();
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();
    @PostMapping("/regist")
    @Operation(summary = "문진서 등록")
    public ResponseEntity<Void> registQuestionnaire(@RequestParam("questionnaireData") String questionnarieData, @RequestParam(value = "image", required = false) MultipartFile file){
        log.info("문진서 등록 정보 : {}", questionnarieData);
        try{
            QuestionnaireRequestDto questionRequestDto = new ObjectMapper().readValue(questionnarieData, QuestionnaireRequestDto.class);
            questionnaireService.createQuestionnaire(questionRequestDto, file);

            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/detail/{questionSeq}")
    @Operation(summary = "문진서 상세보기")
    public ResponseEntity<?> getQuestionnaireDetail(@PathVariable Long questionSeq) {

        try{
            Questionnaire questionnaire = questionnaireService.getQuestionnaireDetail(questionSeq);
            QuestionnaireResponseDto questionnaireResponseDto = QuestionnaireResponseDto.builder()
                    .reserveSeq(questionnaire.getReserve().getSeq())
                    .questionnaireSeq(questionnaire.getSeq())
                    .blood(questionnaire.getBlood())
                    .remark(questionnaire.getRemark())
                    .content(questionnaire.getContent())
                    .title(questionnaire.getTitle())
                    .build();

            UploadFile uploadFile = questionnaire.getUploadFile();
            Path path = Paths.get(uploadPath +"/"+ uploadFile.getName());

            String encodeFile = EncodeFile.encodeFileToBase64(path);
            questionnaireResponseDto.setBase64(encodeFile);

            return ResponseEntity.ok().body(questionnaireResponseDto);


        }catch (IOException e) {
            log.error("문진서 상세보기 실패 : {}", e.getMessage()+"파일을 인코딩하는데 실패하였습니다");
            e.printStackTrace();
            return  ResponseEntity.badRequest().body("문진서 상세보기 실패 : "+e.getMessage());
        }


    }


}
