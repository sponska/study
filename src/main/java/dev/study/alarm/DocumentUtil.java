package dev.study.alarm;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class DocumentUtil {

    public Document getDocument(String url, String keyword) throws IOException {
        Connection.Response response = Jsoup.connect(url + URLEncoder.encode(keyword, "EUC-KR"))
                .method(Connection.Method.GET)
                .execute();
        return response.parse();
    }

    public Elements getItemList(Document document, String cssQuery) {
        return document.select(cssQuery);
    }

    public Element getTopItem(Elements itemList, String cssQuery) {
        return itemList.stream()
                .filter(element -> element.select(cssQuery)
                                           .first() != null)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getTitle(Element topItemInfo) {
        return topItemInfo.html()
                .split("<")[0];
    }

    public String getLink(Element topItemInfo) {
        return topItemInfo.attr("href")
                .substring(1);
    }

    public Element getTopItemInfo(String url, String keyword) throws IOException {
        Document document = getDocument(url, keyword);

        String selectItemListCssQuery = "tr[onmouseover=this.style.backgroundColor='#ffffff']";
        Elements itemList = getItemList(document, selectItemListCssQuery);

        String selectTopItemCssQuery = "td img[src=img/판매.png]";
        Element topItem = getTopItem(itemList, selectTopItemCssQuery);

        return topItem.select("a[target=_self]").first();
    }
}
