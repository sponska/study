package dev.study.alarm.document;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;


public class BoardLifeSiteDocument implements SiteDocument {

    String baseUrl = "http://boardlife.co.kr";
    String boardUrl = "/bbs_list.php?tb=board_used&search_mode=ok&id=&game_id=" +
                      "&happy_board_search_fields%5B%5D=bbs_name" +
                      "&happy_board_search_fields%5B%5D=bbs_review" +
                      "&happy_board_search_fields%5B%5D=bbs_title" +
                      "&happy_board_keyword=%";
    String url = baseUrl + boardUrl;

    String oldTitle;

    String keyword;

    public BoardLifeSiteDocument(String keyword) {
        this.keyword = keyword;
    }

    private Document getDocument(String url, String keyword) throws IOException {
        Connection.Response response = Jsoup.connect(url + URLEncoder.encode(keyword, "EUC-KR"))
                .method(Connection.Method.GET)
                .execute();
        return response.parse();
    }

    private Elements getItemList(Document document, String cssQuery) {
        return document.select(cssQuery);
    }

    private Element getTopItem(Elements itemList, String cssQuery) {
        return itemList.stream()
                .filter(element -> element.select(cssQuery)
                                           .first() != null)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getTitle() throws IOException {
        return getTopItemInfo().html()
                .split("<")[0];
    }

    public String getLink() throws IOException {
        return getTopItemInfo().attr("href")
                .substring(1);
    }

    public Element getTopItemInfo() throws IOException {
        Document document = getDocument(url, keyword);

        String selectItemListCssQuery = "tr[onmouseover=this.style.backgroundColor='#ffffff']";
        Elements itemList = getItemList(document, selectItemListCssQuery);

        String selectTopItemCssQuery = "td img[src=img/판매.png]";
        Element topItem = getTopItem(itemList, selectTopItemCssQuery);

        return topItem.select("a[target=_self]").first();
    }

    @Override
    public boolean isNewItem() throws IOException {
        String title = getTitle();
        boolean isNew = !title.equals(oldTitle);
        if(isNew){
            oldTitle = title;
        }
        return isNew;
    }

    @Override
    public String getText() throws IOException {
        return getTitle() + " \n " + baseUrl + getLink();
    }
}
