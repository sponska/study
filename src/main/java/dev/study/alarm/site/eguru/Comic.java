package dev.study.alarm.site.eguru;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Comparator;
import java.util.function.ToIntFunction;

public class Comic extends Eguru {

    private static final String SLASH = "/";

    String keyword;

    public Comic(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public Element getTopElement(Document document) {
        return document.select("a[target=_blank]")
                .stream()
                .filter(element -> element.text()
                        .contains("화"))
                .max(Comparator.comparingInt(getElementToIntFunction()))
                .orElseThrow(IllegalArgumentException::new);
    }

    private ToIntFunction<Element> getElementToIntFunction() {
        return element -> Integer.parseInt(element.text()
                .replaceAll("[^0-9]", "")
                .substring(0, 3));
    }

    @Override
    public String getLink(Element element) {
        return getBaseUrl() + SLASH + element.attr("href")
                .substring(1);
    }

    @Override
    public String getTitle(Element element) {
        return element.text();
    }

    @Override
    public String getUrl() {
        return getBaseUrl() + SLASH + keyword;
    }
}
