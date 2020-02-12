package dev.study.alarm.messenger;

import org.codehaus.jettison.json.JSONException;

import java.io.IOException;

public interface Messenger {
    void send(String text) throws JSONException, IOException;
}
