package com.aditya.minisearch;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WebCache {

    public static String getPage(String name, String url) throws Exception {
        File cacheFolder = new File("cache");

        if (!cacheFolder.exists()) {
            cacheFolder.mkdir();
        }

        File cachedFile = new File("cache/" + name + ".txt");

        if (cachedFile.exists()) {
            System.out.println("Loading from cache: " + name);
            return Files.readString(cachedFile.toPath());
        }

        System.out.println("Downloading: " + url);

        String content = WebPageLoader.loadPage(url);

        Files.writeString(cachedFile.toPath(), content);

        return content;
    }
}