package dev.study.alarm.site;

import dev.study.alarm.document.SiteDocument;
import dev.study.alarm.notifier.Notifier;
import lombok.RequiredArgsConstructor;
import org.codehaus.jettison.json.JSONException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class BoardLife implements Site {

    private final Notifier notifier;
    private final JdbcTemplate jdbcTemplate;

    public void notifyNewItem(SiteDocument siteDocument) throws IOException, JSONException {
        String keyword = siteDocument.getKeyword();
        String oldTitle = getOldTitle(keyword);
        System.out.println(oldTitle);
        System.out.println(siteDocument.isNewItem(oldTitle));
        if (siteDocument.isNewItem(oldTitle)) {
            String title = siteDocument.getTitle();
            System.out.println(title);
            updateTitle(title, keyword);
            String text = siteDocument.getText();
            notifier.notify(text);
        }
    }

    private void updateTitle(String title, String keyword) {
        jdbcTemplate.update("UPDATE OLD_ITEM SET TITLE = ? WHERE KEYWORD = ?", title, keyword);
    }

    private String getOldTitle(String keyword) {
        System.out.println(keyword);
        try {
            return jdbcTemplate.queryForObject("SELECT TITLE FROM OLD_ITEM WHERE KEYWORD = ?", new String[]{keyword}, String.class);
        } catch (Exception e) {
            jdbcTemplate.update("INSERT INTO OLD_ITEM (KEYWORD,TITLE) VALUES(?,?)",keyword,"");
            return null;
        }
    }
}
