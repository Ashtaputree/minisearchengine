
package com.aditya.minisearch;
import java.util.*;

public class SnippetGenerator {

    public static String generate(String content, String query) {
        if (content == null || content.isEmpty()) {
            return "";
        }

        List<String> queryWords = Tokenizer.tokenize(query);

        if (queryWords.isEmpty()) {
            return content.substring(0, Math.min(250, content.length())) + "...";
        }

        String lowerContent = content.toLowerCase();

        int bestIndex = findBestIndex(lowerContent, queryWords);

        if (bestIndex == -1) {
            return content.substring(0, Math.min(250, content.length())) + "...";
        }

        int start = Math.max(0, bestIndex - 140);
        int end = Math.min(content.length(), bestIndex + 220);

        return "..." + content.substring(start, end) + "...";
    }

    private static int findBestIndex(String lowerContent, List<String> queryWords) {
        int bestIndex = -1;
        int bestScore = -1;

        for (String word : queryWords) {
            int index = lowerContent.indexOf(word.toLowerCase());

            while (index != -1) {
                int score = scoreWindow(lowerContent, queryWords, index);

                if (score > bestScore) {
                    bestScore = score;
                    bestIndex = index;
                }

                index = lowerContent.indexOf(word.toLowerCase(), index + 1);
            }
        }

        return bestIndex;
    }

    private static int scoreWindow(String lowerContent, List<String> queryWords, int center) {
        int start = Math.max(0, center - 120);
        int end = Math.min(lowerContent.length(), center + 180);

        String window = lowerContent.substring(start, end);

        int score = 0;

        for (String word : queryWords) {
            if (window.contains(word.toLowerCase())) {
                score++;
            }
        }

        return score;
    }
}