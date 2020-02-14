package dev.study.alarm.task;

import dev.study.alarm.messenger.Messenger;
import dev.study.alarm.site.BoardLife;
import dev.study.alarm.site.Eguru;
import dev.study.alarm.site.Site;
import dev.study.alarm.utill.JdbcUtil;
import lombok.RequiredArgsConstructor;
import org.codehaus.jettison.json.JSONException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Notifier {

    private final Messenger messenger;
    private final JdbcUtil jdbcUtil;

    @Scheduled(cron = "0 0/5 * * * ?")
    private void task() throws IOException, JSONException {
        List<Site> sites = Arrays.asList(new Eguru("kingdom")
                ,new Eguru("one-punch-man-re")
                ,new BoardLife("크레이지 타임")
        );

        for (Site site : sites) {
            notifyNewItem(site);
        }
    }

    private void notifyNewItem(Site site) throws IOException, JSONException {
        String keyword = site.getKeyword();
        if (site.isNewItem(jdbcUtil.getOldTitle(keyword))) {
            jdbcUtil.updateTitle(site.getTitle(), keyword);
            String text = site.getText();
            messenger.send(text);
        }
    }
}
