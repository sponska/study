package dev.study.alarm.notifier;

import org.codehaus.jettison.json.JSONException;

import java.io.IOException;

public interface Notifier {
    void notify(String text) throws JSONException, IOException;
}
