package dev.study.alarm.task;

import dev.study.alarm.messenger.Messenger;
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

    @Scheduled(cron = "0 0/1 * * * ?")
    private void task() throws IOException, JSONException {

        List<Site> sites = Arrays.asList(
                BoardLife.of(messenger, jdbcUtil, Collections.singletonList(new UsedBoard("크레이지 타임")))
                , Eguru.of(messenger, jdbcUtil, Arrays.asList(new Comic("kingdom"), new Comic("one-punch-man-re")))
        );

        for (Site site : sites) {
            site.notifyNewItem();
        }
    }
}
