package com.aditya.minisearch;
import java.util.*;

public class SpellCorrector {
    public static int editDistance(String a, String b) {
        int m = a.length();
        int n = b.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1])
                    );
                }
            }
        }

        return dp[m][n];
    }

    public static String normalizeRepeatedChars(String word) {
        return word.replaceAll("(.)\\1+", "$1");
    }

   public static String findClosestWord(String word, Set<String> vocabulary) {
    String normalizedWord = normalizeRepeatedChars(word);

    String closest = null;
    int bestDistance = Integer.MAX_VALUE;

    for (String candidate : vocabulary) {
        String normalizedCandidate = normalizeRepeatedChars(candidate);
        int distance = editDistance(normalizedWord, normalizedCandidate);

        if (distance < bestDistance) {
            bestDistance = distance;
            closest = candidate;
        }
    }

    if (closest == null) {
        return null;
    }

    int maxLength = Math.max(normalizedWord.length(), closest.length());
    double similarity = 1.0 - ((double) bestDistance / maxLength);

    if (bestDistance <= 2 || similarity >= 0.75) {
        return closest;
    }

    return null;
}
}