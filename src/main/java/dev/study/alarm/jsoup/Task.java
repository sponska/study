package dev.study.alarm.jsoup;

import dev.study.alarm.document.BoardLifeSiteDocument;
import dev.study.alarm.site.Site;
import lombok.RequiredArgsConstructor;
import org.codehaus.jettison.json.JSONException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Task {

    private final Site site;

    @Scheduled(cron = "0 0/1 * * * ?")
    private void task() throws IOException, JSONException {
        site.notifyNewItem(new BoardLifeSiteDocument("크레이지 타임"));
    }
}
