package dev.study.alarm.document;


import org.jsoup.nodes.Element;

import java.io.IOException;

public interface SiteDocument {
    boolean isNewItem(String oldTitle) throws IOException;
    String getText() throws IOException;
    String getKeyword();
    String getTitle() throws IOException;
}
