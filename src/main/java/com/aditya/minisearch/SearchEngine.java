
package com.aditya.minisearch;
import java.util.*;

public class SearchEngine {
    private SearchIndex index;
    private Map<String, String> documents;

    public SearchEngine(SearchIndex index, Map<String, String> documents) {
        this.index = index;
        this.documents = documents;
    }

    public List<SearchResult> search(String query) {
        List<String> queryWords = Tokenizer.tokenize(query);
        Map<String, Double> scores = new HashMap<>();

        for (String word : queryWords) {
            if (index.containsWord(word)) {
                addTfIdfScores(word, scores);
            } else {
                String suggestion = SpellCorrector.findClosestWord(word, index.getVocabulary());

                if (suggestion != null) {
                    System.out.println("Did you mean: " + suggestion + "?");
                    addTfIdfScores(suggestion, scores);
                }
            }
        }

        List<Map.Entry<String, Double>> ranked =
                new ArrayList<>(scores.entrySet());

        ranked.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        List<SearchResult> results = new ArrayList<>();

        for (Map.Entry<String, Double> entry : ranked) {
            String docName = entry.getKey();
            double score = entry.getValue();
            String content = documents.get(docName);

            String snippet = SnippetGenerator.generate(content, query);

            SearchResult result = new SearchResult(
                    docName,
                    docName,
                    snippet,
                    score
            );

            results.add(result);
        }

        return results;
    }

    private void addTfIdfScores(String word, Map<String, Double> scores) {
        Map<String, Integer> docCounts = index.getDocumentCounts(word);

        int totalDocs = index.getTotalDocumentCount();
        int docsWithWord = index.getDocumentFrequency(word);

        double idf = Math.log((double) totalDocs / docsWithWord);

        for (Map.Entry<String, Integer> entry : docCounts.entrySet()) {
            String doc = entry.getKey();
            int tf = entry.getValue();

            double tfidf = tf * idf;

            scores.put(doc, scores.getOrDefault(doc, 0.0) + tfidf);
        }
    }
}