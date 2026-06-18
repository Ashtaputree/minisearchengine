package com.aditya.minisearch;
import java.util.*;

public class Crawler {

    public static Map<String, String> crawl(String startUrl, int maxPages) {
        Map<String, String> pages = new LinkedHashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(startUrl);
        visited.add(startUrl);

        while (!queue.isEmpty() && pages.size() < maxPages) {
            String url = queue.poll();

            try {
                System.out.println("Crawling: " + url);

                String content;

                if (CrawlerCache.exists(url)) {
                    System.out.println("Loading cached page: " + url);
                    content = CrawlerCache.loadPage(url);
                } else {
                    System.out.println("Downloading page: " + url);
                    content = WebPageLoader.loadPage(url);
                    CrawlerCache.savePage(url, content);
                }

                pages.put(url, content);

                List<String> links = WebPageLoader.extractLinks(url);
                System.out.println("Links found: " + links.size());

                for (String link : links) {
                    if (!visited.contains(link) && visited.size() < maxPages * 10) {
                        visited.add(link);
                        queue.offer(link);
                    }
                }

            } catch (Exception e) {
                System.out.println("Failed: " + url);
            }
        }

        return pages;
    }
}