package dev.study.alarm.jsoup;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import dev.study.alarm.utill.HttpUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class JsoupTask {

    @Autowired
    Environment environment;

    private static final String BASE_URL = "http://boardlife.co.kr";
    private static final String BOARD_URL = BASE_URL + "/bbs_list.php?tb=board_used&search_mode=ok&id=&game_id=" +
                                            "&happy_board_search_fields%5B%5D=bbs_name" +
                                            "&happy_board_search_fields%5B%5D=bbs_review" +
                                            "&happy_board_search_fields%5B%5D=bbs_title" +
                                            "&happy_board_keyword=%C5%A9%B7%B9%C0%CC%C1%F6+%C5%B8%C0%D3&x=0&y=0";
    public static final String SLACK_CHANNEL = "test";

    private String oldTitle;
    private static final String SLACK_API_URL = "https://slack.com/api/chat.postMessage";

    @Value("${spring.batch.job.names}")
    private String token;

    private String authToken = "Bearer " + token;

    @Scheduled(cron = "0 0/1 * * * ?")
    private void task() throws IOException, JSONException {
        Connection.Response response = org.jsoup.Jsoup.connect(BOARD_URL)
                .method(Connection.Method.GET)
                .execute();

        Document document = response.parse();
        Optional<Element> sellList = getElement(document);

        String title = getTitle(sellList);
        String link = getLink(sellList);

        if (title.equals(oldTitle)) {
            System.out.println("no new item");
        } else {
            oldTitle = title;
            sendSlackMessage(title, link);
        }
    }

    private void sendSlackMessage(String title, String link) throws JSONException, IOException {
        CloseableHttpClient httpClient = HttpUtil.getHttpClient();
        HttpPost httpPost = new HttpPost(SLACK_API_URL);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("channel", SLACK_CHANNEL);
        jsonObject.put("text", title + " \n " + link);

        String jsonMessage = jsonObject.toString();

        StringEntity stringEntity = new StringEntity(jsonMessage, "UTF-8");
        httpPost.setEntity(stringEntity);

        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", authToken);

        System.out.println(token);
        System.out.println(jsonMessage);

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        System.out.println(httpResponse);
    }

    private Optional<Element> getElement(Document document) {
        Elements elements = document.select("tr[onmouseover=this.style.backgroundColor='#ffffff']");

        return elements.stream()
                .filter(element -> element.select("td img[src=img/판매.png]")
                                           .first() != null)
                .findFirst();
    }

    private String getLink(Optional<Element> sellList) {
        return sellList.map(element -> element.select("a[target=_self]")
                .first())
                .map(element -> BASE_URL + element.attr("href")
                        .substring(1))
                .orElse("");
    }

    private String getTitle(Optional<Element> sellList) {
        return sellList
                .map(element -> element.select("a[target=_self]")
                        .first())
                .map(element -> element.html()
                        .split("<")[0])
                .orElse("");
    }
}
