
package com.aditya.minisearch;
import java.util.*;

public class ConsoleDemo {
    public static void main(String[] args) throws Exception {

        Map<String, String> documents = DocumentLoader.loadDocuments("docs");

        SearchIndex index = new SearchIndex();

        for (Map.Entry<String, String> entry : documents.entrySet()) {
            index.addDocument(entry.getKey(), entry.getValue());
        }

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
    webPages.put(url, CrawlerCache.exists(url)
            ? CrawlerCache.loadPage(url)
            : WebPageLoader.loadPage(url));

    if (!CrawlerCache.exists(url)) {
        CrawlerCache.savePage(url, webPages.get(url));
    }
}

        for (Map.Entry<String, String> entry : webPages.entrySet()) {
            index.addDocument(entry.getKey(), entry.getValue());
            documents.put(entry.getKey(), entry.getValue());
        }

        SearchEngine engine = new SearchEngine(index, documents);

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter search query: ");
        String query = sc.nextLine();

        List<SearchResult> results = engine.search(query);

        if (results.isEmpty()) {
            System.out.println("No documents found.");
        } else {
            System.out.println("\nRanked Results:");

            for (SearchResult result : results) {
                System.out.printf(
                        "Document: %s | Score: %.4f%n",
                        result.getTitle(),
                        result.getScore()
                );
            }

            SearchResult topResult = results.get(0);

            System.out.println("\nTop Result Snippet:");
            System.out.println(topResult.getSnippet());
        }

        sc.close();
    }
}