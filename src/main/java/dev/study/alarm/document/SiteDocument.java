package dev.study.alarm.document;


import org.jsoup.nodes.Element;

import java.io.IOException;

public interface SiteDocument {
    boolean isNewItem() throws IOException;
    String getText() throws IOException;
}
