
package com.aditya.minisearch;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchIndex implements Serializable {
   private static final long serialVersionUID = 1L;

    private Map<String, Map<String,Integer>> index = new HashMap<>();
    private Set<String> documents = new HashSet<>();

    public void addDocument(String docName, String content) {
        documents.add(docName);

        List<String> words = Tokenizer.tokenize(content);

        for (String word : words) {
            index.putIfAbsent(word, new HashMap<>());

            Map<String, Integer> docCounts = index.get(word);

            docCounts.put(docName, docCounts.getOrDefault(docName, 0) + 1);
        }
    }

    public boolean containsWord(String word) {
        return index.containsKey(word);
    }

    public Map<String, Integer> getDocumentCounts(String word) {
        return index.getOrDefault(word, new HashMap<>());
    }

    public Set<String> getDocuments(String word) {
        return index.containsKey(word)
                ? index.get(word).keySet()
                : new HashSet<>();
    }

    public Set<String> getVocabulary() {
        return index.keySet();
    }

    public int getTotalDocumentCount() {
        return documents.size();
    }

    public int getDocumentFrequency(String word) {
        if (!index.containsKey(word)) {
            return 0;
        }

        return index.get(word).size();
    }
}