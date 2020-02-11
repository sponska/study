package dev.study.alarm.site;

import org.codehaus.jettison.json.JSONException;

import java.io.IOException;

public interface Site {
    void notifyNewItem(String keyword) throws IOException, JSONException;
}
