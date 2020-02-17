package dev.study.alarm.site;


import dev.study.alarm.messenger.Messenger;
import dev.study.alarm.utill.JdbcUtil;
import org.codehaus.jettison.json.JSONException;

import java.io.IOException;
import java.util.List;

public abstract class Site {

    private JdbcUtil jdbcUtil;
    private Messenger messenger;
    private List<? extends Selector> selectors;

    public Site(Messenger messenger, JdbcUtil jdbcUtil, List<? extends Selector> selectors) {
        this.jdbcUtil = jdbcUtil;
        this.messenger = messenger;
        this.selectors = selectors;
    }

    public void notifyNewItem() throws IOException, JSONException {
        for (Selector selector : selectors) {
            String keyword = selector.getKeyword();
            TopItem topItem = selector.getTopItem();

            String title = topItem.getTitle();
            if (!jdbcUtil.getOldTitle(keyword).equals(title)) {
                messenger.send(topItem.getText());
                jdbcUtil.updateTitle(keyword, title);
            }
        }
    }
}
