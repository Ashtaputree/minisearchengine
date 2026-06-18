package com.aditya.minisearch;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class DocumentLoader {
    public static Map<String, String> loadDocuments(String folderPath) throws IOException {
        Map<String, String> documents = new HashMap<>();

        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            throw new IOException("Folder does not exist: " + folderPath);
        }

        File[] files = folder.listFiles();

        if (files == null) {
            return documents;
        }

        for (File file : files) {
            if (file.isFile()) {
                String content = Files.readString(file.toPath());
                documents.put(file.getName(), content);
            }
        }

        return documents;
    }
}