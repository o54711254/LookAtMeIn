package com.ssafy.lam.search.controller;

import com.ssafy.lam.search.service.SearchService;
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
    public ResponseEntity<List<?>> search(
            @RequestParam String keyword,
            @RequestParam(required = false) String category) {
        List<?> results = searchService.search(keyword, category);
        return ResponseEntity.ok(results);
    }
}
