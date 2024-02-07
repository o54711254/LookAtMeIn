package com.ssafy.lam.questionnaire.controller;


import com.ssafy.lam.questionnaire.dto.QuestionnaireRequestDto;
import com.ssafy.lam.questionnaire.service.QuestionnaireService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questionnaire")
@RequiredArgsConstructor
public class QuestionnaireController {

    private Logger log = LoggerFactory.getLogger(QuestionnaireController.class);
    private final QuestionnaireService questionnaireService;
    @PostMapping("/regist")
    @Operation(summary = "문진서 등록")
    public void registQuestionnaire(@ModelAttribute QuestionnaireRequestDto questionRequestDto) {
        log.info("문진서 등록 정보 : {}", questionRequestDto);

        questionnaireService.createQuestionnaire(questionRequestDto);


    }

//    @GetMapping("/detail/{past_reserve_seq}")
//    @Operation(summary = "문진서 상세보기")
    


    @GetMapping()

}
