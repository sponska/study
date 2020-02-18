package dev.study.alarm.site;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public abstract class Site {

    String keyword;

    public Document getDocument() throws IOException {
        Connection.Response response = Jsoup.connect(getUrl())
                .method(Connection.Method.GET)
                .execute();
        return response.parse();
    }

    public TopItem getTopItem() throws IOException {
        Element topElement = getTopElement(getDocument());
        String title = getTitle(topElement);
        String link = getLink(topElement);
        return new TopItem(title, link ,keyword);
    }

    abstract String getLink(Element element);

    abstract String getTitle(Element element);

    abstract Element getTopElement(Document document) throws IOException;

    abstract String getUrl();
}
