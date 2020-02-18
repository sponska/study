package dev.study.alarm.site;

import dev.study.alarm.messenger.Messenger;
import dev.study.alarm.site.boardLife.UsedBoard;
import dev.study.alarm.site.eguru.Comic;
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

        List<Site> sites = Arrays.asList(
                new UsedBoard("크레이지 타임"),
                new Comic("kingdom"),
                new Comic("one-punch-man-re")
        );

        for (Site site : sites) {
            notifyNewItem(site);
        }
    }

    public void notifyNewItem(Site site) throws IOException, JSONException {

        TopItem topItem = site.getTopItem();
        String title = topItem.getTitle();
        String keyword = topItem.getKeyword();

        boolean haveNewItem = !jdbcUtil.getOldTitle(keyword)
                .equals(title);

        if (haveNewItem) {
            messenger.send(topItem.getText());
            jdbcUtil.updateTitle(keyword, title);
        }

    }
}
