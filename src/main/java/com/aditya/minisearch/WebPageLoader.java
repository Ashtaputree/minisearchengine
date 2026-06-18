package com.aditya.minisearch;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class WebPageLoader {

    public static String loadPage(String url) throws Exception {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .get();

        Element article = doc.selectFirst("div.mw-parser-output");

        if (article != null) {
    article.select("table").remove();
    article.select(".navbox").remove();
    article.select(".metadata").remove();
    article.select(".reference").remove();
    article.select(".reflist").remove();
    article.select(".toc").remove();

    return article.text();
}
        return doc.text();
    }

    public static List<String> extractLinks(String url) throws Exception {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .get();

        Elements links = doc.select("a[href]");
        List<String> result = new ArrayList<>();

        links.forEach(link -> {
            String absUrl = link.absUrl("href");

            if (absUrl.isEmpty()) {
                return;
            }

            

            String path = absUrl.replace("https://en.wikipedia.org/wiki/", "");

if (absUrl.startsWith("https://en.wikipedia.org/wiki/")
        && !path.contains(":")
        && !path.contains("#")
        && !path.equals("Main_Page")) {

    result.add(absUrl);
}
        });

        return result;
    }
}