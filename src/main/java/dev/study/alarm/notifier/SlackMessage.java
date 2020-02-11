package dev.study.alarm.notifier;

import com.google.common.base.Charsets;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.http.entity.StringEntity;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Data
@AllArgsConstructor
public class SlackMessage {

    private String channel;
    private String text;

    public StringEntity getStringEntity() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("channel", channel);
        jsonObject.put("text", text);
        String jsonMessage = jsonObject.toString();
        return new StringEntity(jsonMessage, Charsets.UTF_8);
    }


}
