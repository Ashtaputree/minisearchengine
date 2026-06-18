package com.aditya.minisearch;
import java.util.*;

public class Tokenizer {
    public static List<String> tokenize(String text) {
        String clean = text.toLowerCase().replaceAll("[^a-z0-9 ]", "");
        String[] parts = clean.split("\\s+");

        List<String> tokens = new ArrayList<>();

        for (String word : parts) {
            if (!word.isEmpty()) {
                tokens.add(word);
            }
        }

        return tokens;
    }
}