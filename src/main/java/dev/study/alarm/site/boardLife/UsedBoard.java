package dev.study.alarm.site.boardLife;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UsedBoard extends BoardLife {

    private static final String SLASH = "/";
    String boardUrl = "/bbs_list.php?tb=board_used&search_mode=ok&id=&game_id=" +
                      "&happy_board_search_fields%5B%5D=bbs_name" +
                      "&happy_board_search_fields%5B%5D=bbs_review" +
                      "&happy_board_search_fields%5B%5D=bbs_title" +
                      "&happy_board_keyword=";

    String keyword;

    public UsedBoard(String keyword) {
        this.keyword = keyword;
    }

    public Element getTopElement(Document document) {
        return document.select("tr[onmouseover=this.style.backgroundColor='#ffffff']")
                .stream()
                .filter(element -> element.selectFirst("td img[src=img/판매.png]") != null)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .selectFirst("a[target=_self]");
    }

    public String getTitle(Element element) {
        return element.html()
                .split("<")[0];
    }

    public String getLink(Element element) {
        String baseUrl = getBaseUrl();
        return baseUrl + SLASH + element.attr("href")
                .substring(1);
    }

    public String getUrl() {
        String keyword = getKeyword();
        String baseUrl = getBaseUrl();
        try {
            return baseUrl + boardUrl + URLEncoder.encode(keyword, "EUC-KR");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return baseUrl + boardUrl + keyword;
        }
    }

}
