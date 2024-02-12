package com.ssafy.lam.search.controller;

import com.ssafy.lam.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/search")
    @Operation(summary = "검색 : 제목,내용,부위,병원이름 등 넣으면 검색 (category는 freeboard,hospital 등 빈값으로 넣으면 전체)")
    public ResponseEntity<List<?>> search(@RequestParam String keyword, @RequestParam(required = false) String category) {
        System.out.println("키워드카테고리"+keyword + " " + category);
        List<?> results = searchService.search(keyword, category);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }
        return ResponseEntity.ok(results);
    }
}
