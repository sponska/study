package dev.study.alarm.site;


import lombok.Getter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

@Getter
public abstract class Site {

    private static final String SLASH = "/";
    private String keyword;
    public String baseUrl;

    public String getKeyword() {
        return keyword;
    }

    public Document getDocument() throws IOException {
        Connection.Response response = Jsoup.connect(getUrl())
                .method(Connection.Method.GET)
                .execute();
        return response.parse();
    }

    public boolean isNewItem(String oldTitle) throws IOException {
        String title = getTitle();
        return !title.equals(oldTitle);
    }

    public String getText() throws IOException {
        return getTitle() + " \n " + getLink();
    }

    public Element getTopItem(Elements itemList, String cssQuery) {
        return itemList.stream()
                .filter(element -> element.select(cssQuery)
                                           .first() != null)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getLink() throws IOException {
        return getBaseUrl() + SLASH + getTopItemInfo().attr("href")
                .substring(1);
    }

    public Elements getItemList(Document document, String cssQuery) {
        return document.select(cssQuery);
    }

    abstract Element getTopItemInfo() throws IOException;

    public abstract String getTitle() throws IOException;

    abstract String getUrl();
}
