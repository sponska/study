package dev.study.alarm.site.boardLife;

import dev.study.alarm.site.Site;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public abstract class BoardLife extends Site {

    public String getBaseUrl() {
        return "http://boardlife.co.kr";
    }

    public abstract String getLink(Element element);

    public abstract String getTitle(Element element);

    public abstract Element getTopElement(Document document);

    public abstract String getUrl();

}
