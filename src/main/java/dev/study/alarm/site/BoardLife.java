package dev.study.alarm.site;

import dev.study.alarm.DocumentUtil;
import dev.study.alarm.notifier.Notifier;
import lombok.RequiredArgsConstructor;
import org.codehaus.jettison.json.JSONException;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class BoardLife implements Site {

    private final Notifier notifier;
    private final DocumentUtil documentUtil;

    String baseUrl = "http://boardlife.co.kr";
    String boardUrl = "/bbs_list.php?tb=board_used&search_mode=ok&id=&game_id=" +
                      "&happy_board_search_fields%5B%5D=bbs_name" +
                      "&happy_board_search_fields%5B%5D=bbs_review" +
                      "&happy_board_search_fields%5B%5D=bbs_title" +
                      "&happy_board_keyword=%";

    public String oldItemTitle;

    public void notifyNewItem(String keyword) throws IOException, JSONException {
        Element topItemInfo = documentUtil.getTopItemInfo(baseUrl + boardUrl, keyword);

        if (isNewItem(topItemInfo)) {
            oldItemTitle = documentUtil.getTitle(topItemInfo);
            String text = getText(topItemInfo);
            notifier.notify(text);
        }
    }

    public boolean isNewItem(Element itemInfo) {
        String itemTitle = documentUtil.getTitle(itemInfo);
        return !itemTitle.equals(oldItemTitle);
    }

    private String getText(Element topItemInfo) {
        return documentUtil.getTitle(topItemInfo) + " \n " + baseUrl + documentUtil.getLink(topItemInfo);
    }

}
