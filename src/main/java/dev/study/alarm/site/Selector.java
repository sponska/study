package dev.study.alarm.site;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public interface Selector {
    Document getDocument() throws IOException;

    TopItem getTopItem() throws IOException;

    String getLink(Element element);

    String getTitle(Element element);

    Element getTopElement(Document document) throws IOException;

    String getUrl();

    String getKeyword();
}
