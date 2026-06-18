package com.aditya.minisearch;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/health")
    public String health() {
        return "MiniSearch is running!";
    }

    @GetMapping("/search")
    public List<SearchResult> search(
            @RequestParam("q") String q
    ) {
        return searchService.search(q);
    }
}