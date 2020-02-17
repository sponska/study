package dev.study.alarm.site.eguru;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Comparator;
import java.util.function.ToIntFunction;

public class Comic extends EguruSelector {

    private static final String SLASH = "/";

    public Comic(String keyword) {
        super(keyword);
    }

    @Override
    public Element getTopElement(Document document) throws IOException {
        return document.select("a[target=_blank]").stream().filter(element -> element.text()
                .contains("화"))
                .max(Comparator.comparingInt(getElementToIntFunction()))
                .orElseThrow(IllegalArgumentException::new);
    }

    private ToIntFunction<Element> getElementToIntFunction() {
        return element -> Integer.parseInt(element.text().replaceAll("[^0-9]","").substring(0,3));
    }

    @Override
    public String getLink(Element element) {
       return baseUrl + SLASH + element.attr("href")
                .substring(1);
    }

    @Override
    public String getTitle(Element element) {
        return element.text();
    }

    @Override
    public String getUrl() {
        return baseUrl + SLASH + getKeyword();
    }
}
