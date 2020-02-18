package dev.study.alarm.site.eguru;

import dev.study.alarm.site.Site;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public abstract class Eguru extends Site {

    public String getBaseUrl(){
        return "https://eguru.tumblr.com";
    }

    public abstract String getLink(Element element);
    public abstract String getTitle(Element element);
    public abstract Element getTopElement(Document document);
    public abstract String getUrl();
}
