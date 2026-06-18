package com.aditya.minisearch;
import java.io.File;
import java.nio.file.Files;

public class CrawlerCache {

    public static void savePage(String name, String content) throws Exception {
        File folder = new File("crawler-cache");

        if (!folder.exists()) {
            folder.mkdir();
        }

        String safeName = name
                .replace("https://en.wikipedia.org/wiki/", "")
                .replaceAll("[^a-zA-Z0-9_-]", "_");

        File file = new File(folder, safeName + ".txt");

        Files.writeString(file.toPath(), content);
    }

    public static boolean exists(String name) {
        String safeName = name
                .replace("https://en.wikipedia.org/wiki/", "")
                .replaceAll("[^a-zA-Z0-9_-]", "_");

        File file = new File("crawler-cache/" + safeName + ".txt");

        return file.exists();
    }

    public static String loadPage(String name) throws Exception {
        String safeName = name
                .replace("https://en.wikipedia.org/wiki/", "")
                .replaceAll("[^a-zA-Z0-9_-]", "_");

        File file = new File("crawler-cache/" + safeName + ".txt");

        return Files.readString(file.toPath());
    }
}
