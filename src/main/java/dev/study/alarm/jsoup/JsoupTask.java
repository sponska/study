package dev.study.alarm.jsoup;

import dev.study.alarm.site.Site;
import lombok.RequiredArgsConstructor;
import org.codehaus.jettison.json.JSONException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JsoupTask {

    private final Site site;

    @Scheduled(cron = "0 0/1 * * * ?")
    private void task() throws IOException, JSONException {
        site.notifyNewItem("크레이지 타임");
    }
}
