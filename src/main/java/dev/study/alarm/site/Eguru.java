package dev.study.alarm.site;

import lombok.Getter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Comparator;

@Getter
public class Eguru extends Site {

    String baseUrl = "https://eguru.tumblr.com/";

    String keyword;

    public Eguru(String keyword) {
        this.keyword = keyword;
    }

    public String getText() throws IOException {
        return getTitle() + " \n " + getLink();
    }

    public String getTitle() throws IOException {
        return getTopItemInfo().text();
    }

    public Elements getItemList(Document document, String cssQuery) {
        return document.select(cssQuery);
    }

    public String getLink() throws IOException {
        return baseUrl + getTopItemInfo().attr("href")
                .substring(1);
    }

    public Element getTopItemInfo() throws IOException {
        Document document = getDocument();

        String selectItemListCssQuery = "a[target=_blank]";
        Elements itemList = getItemList(document, selectItemListCssQuery);

        return itemList.stream()
                .filter(element -> element.text()
                        .contains("화"))
                .max(Comparator.comparingInt(o -> Integer.parseInt(o.text()
                        .substring(3, o.text()
                                              .length() - 1))))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    String getUrl() {
        return this.baseUrl + this.keyword;
    }

}
