package dev.study.alarm.site.eguru;

import dev.study.alarm.site.Selector;
import dev.study.alarm.site.TopItem;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public abstract class EguruSelector implements Selector {
    public String baseUrl = "https://eguru.tumblr.com";

    private String keyword;

    public EguruSelector(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword(){
        return this.keyword;
    };

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
        return new TopItem(title, link);
    }
    public abstract String getLink(Element element);
    public abstract String getTitle(Element element);
    public abstract Element getTopElement(Document document) throws IOException;
    public abstract String getUrl();
}
