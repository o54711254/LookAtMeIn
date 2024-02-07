package com.ssafy.lam.questionnaire.controller;


import com.ssafy.lam.questionnaire.dto.QuestionnaireRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {


    @PostMapping("/regist")
    @Operation(summary = "문진서 등록")
    public void registQuestionnaire(@ModelAttribute QuestionnaireRequestDto questionRequestDto) {


    }

}
