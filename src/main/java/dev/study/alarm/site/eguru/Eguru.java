package dev.study.alarm.site.eguru;

import dev.study.alarm.messenger.Messenger;
import dev.study.alarm.site.Site;
import dev.study.alarm.utill.JdbcUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public abstract class Eguru extends Site {

    public String getBaseUrl(){
        return "https://eguru.tumblr.com";
    }

    public abstract String getLink(Element element);
    public abstract String getTitle(Element element);
    public abstract Element getTopElement(Document document);
    public abstract String getUrl();
}
