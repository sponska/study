package dev.study.alarm.messenger;

import dev.study.alarm.utill.HttpUtil;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Slack implements Messenger {

    private static final String SLACK_API_URL = "https://slack.com/api/chat.postMessage";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String SLACK_CHANNEL = "test";
    public static final String CHARACTER_SPACE = " ";

    @Value("${slack.token}")
    private String token;

    public void send(String text) throws JSONException, IOException {
        CloseableHttpClient httpClient = HttpUtil.getHttpClient();
        HttpPost httpPost = new HttpPost(SLACK_API_URL);

        SlackMessage slackMessage = new SlackMessage(SLACK_CHANNEL, text);
        httpPost.setEntity(slackMessage.getStringEntity());

        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

        String authToken = TOKEN_PREFIX + CHARACTER_SPACE + token;
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authToken);

        httpClient.execute(httpPost);
    }
}
