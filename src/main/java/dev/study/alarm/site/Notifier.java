package dev.study.alarm.site;

import dev.study.alarm.messenger.Messenger;
import dev.study.alarm.site.Selector;
import dev.study.alarm.site.TopItem;
import dev.study.alarm.site.eguru.Comic;
import dev.study.alarm.site.boardLife.UsedBoard;
import dev.study.alarm.site.boardLife.BoardLife;
import dev.study.alarm.site.eguru.Eguru;
import dev.study.alarm.site.Site;
import dev.study.alarm.utill.JdbcUtil;
import lombok.RequiredArgsConstructor;
import org.codehaus.jettison.json.JSONException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Notifier {

    private final Messenger messenger;
    private final JdbcUtil jdbcUtil;

    @Scheduled(cron = "0 0/5 * * * ?")
    private void task() throws IOException, JSONException {


        List<Site> sites = Arrays.asList(new UsedBoard("크레이지 타임"));
        for (Site site : sites) {
            notifyNewItem(site);
        }
    }

    public void notifyNewItem(Site site) throws IOException, JSONException {
            String keyword = site.getKeyword();
            TopItem topItem = site.getTopItem();

            String title = topItem.getTitle();
            if (!jdbcUtil.getOldTitle(keyword).equals(title)) {
                messenger.send(topItem.getText());
                jdbcUtil.updateTitle(keyword, title);
            }
    }
}
