package dev.study.alarm.site;

import dev.study.alarm.document.SiteDocument;
import org.codehaus.jettison.json.JSONException;

import java.io.IOException;

public interface Site {
    void notifyNewItem(SiteDocument siteDocument) throws IOException, JSONException;
}
