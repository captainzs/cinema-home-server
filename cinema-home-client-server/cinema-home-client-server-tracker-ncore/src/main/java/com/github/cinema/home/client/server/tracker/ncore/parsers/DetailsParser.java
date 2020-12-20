package com.github.cinema.home.client.server.tracker.ncore.parsers;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class DetailsParser {
    public String parseDownloadToken(Document html) throws InvalidArgumentException {
        Elements downloadElements = html.getElementsByClass("download");
        if (downloadElements.isEmpty()) {
            throw new InvalidArgumentException("Download url not exists in the given html!");
        }
        Element downloadElement = downloadElements.first();
        String urlPart = downloadElement.child(0).attr("href");
        return urlPart.substring(urlPart.indexOf("key=") + 4);
    }
}
