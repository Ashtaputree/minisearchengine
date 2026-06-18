package com.aditya.minisearch;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {

    private final SearchEngine engine;

    public SearchService() throws Exception {

        Map<String, String> documents = DocumentLoader.loadDocuments("docs");
        Map<String, String> webPages = new LinkedHashMap<>();

String[] urls = {
        "https://en.wikipedia.org/wiki/Java_(programming_language)",
        "https://en.wikipedia.org/wiki/Java_virtual_machine",
        "https://en.wikipedia.org/wiki/Java_bytecode",
        "https://en.wikipedia.org/wiki/Garbage_collection_(computer_science)",
        "https://en.wikipedia.org/wiki/Compiler",
        "https://en.wikipedia.org/wiki/Object-oriented_programming",
        "https://en.wikipedia.org/wiki/Programming_language",
        "https://en.wikipedia.org/wiki/Python_(programming_language)",
        "https://en.wikipedia.org/wiki/JavaScript",
        "https://en.wikipedia.org/wiki/Data_structure"
};

for (String url : urls) {

    String content;

    if (CrawlerCache.exists(url)) {
        content = CrawlerCache.loadPage(url);
    } else {
        content = WebPageLoader.loadPage(url);
        CrawlerCache.savePage(url, content);
    }

    webPages.put(url, content);
}
documents.putAll(webPages);

        SearchIndex index = new SearchIndex();

        for (Map.Entry<String, String> entry : documents.entrySet()) {
            index.addDocument(entry.getKey(), entry.getValue());
        }

        this.engine = new SearchEngine(index, documents);
    }

    public List<SearchResult> search(String query) {
        return engine.search(query);
    }
}