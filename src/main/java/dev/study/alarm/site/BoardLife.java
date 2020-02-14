package dev.study.alarm.site;

import lombok.Getter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Getter
public class BoardLife extends Site {

    public String baseUrl = "http://boardlife.co.kr";
    String boardUrl = "/bbs_list.php?tb=board_used&search_mode=ok&id=&game_id=" +
                      "&happy_board_search_fields%5B%5D=bbs_name" +
                      "&happy_board_search_fields%5B%5D=bbs_review" +
                      "&happy_board_search_fields%5B%5D=bbs_title" +
                      "&happy_board_keyword=";
    String url = baseUrl + boardUrl;
    String keyword;

    public BoardLife(String keyword) {
        this.keyword = keyword;
    }

    public Element getTopItemInfo() throws IOException {
        Document document = getDocument();

        String selectItemListCssQuery = "tr[onmouseover=this.style.backgroundColor='#ffffff']";
        Elements itemList = getItemList(document, selectItemListCssQuery);

        String selectTopItemCssQuery = "td img[src=img/판매.png]";
        Element topItem = getTopItem(itemList, selectTopItemCssQuery);

        return topItem.select("a[target=_self]")
                .first();
    }

    public String getTitle() throws IOException {
        return getTopItemInfo().html()
                .split("<")[0];
    }

    public String getUrl() {
        try {
            return url + URLEncoder.encode(keyword, "EUC-KR");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return url + keyword;
        }
    }
}
