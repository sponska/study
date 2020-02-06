package dev.study.alarm.jsoup;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class JsoupTask {

    private static final String BASE_URL = "http://boardlife.co.kr";
    private static final String BOARD_URL = BASE_URL + "/bbs_list.php?tb=board_used&search_mode=ok&id=&game_id=&happy_board_search_fields%5B%5D=bbs_name&happy_board_search_fields%5B%5D=bbs_review&happy_board_search_fields%5B%5D=bbs_title&happy_board_keyword=%C5%A9%B7%B9%C0%CC%C1%F6+%C5%B8%C0%D3&x=28&y=16";

    @Scheduled(cron = "0 0/1 * * * ?")
    private void task() throws IOException {
        Connection.Response response = org.jsoup.Jsoup.connect(BOARD_URL)
                .method(Connection.Method.GET)
                .execute();

        Document document = response.parse();

        Elements elements = document.select("tr[onmouseover=this.style.backgroundColor='#ffffff']");

        Optional<Element> sellList = elements.stream()
                .filter(element -> element.select("td img[src=img/판매.png]").first() != null)
                .findFirst();

        String title = sellList
                .map(element -> element.select("a[target=_self]")
                        .first())
                .map(element -> element.html()
                        .split("<")[0])
                .orElse("");

        String link = sellList.map(element -> element.select("a[target=_self]")
                .first())
                .map(element -> BASE_URL + element.attr("href")
                        .substring(1))
                .orElse("");

        System.out.println(title);
        System.out.println(link);
    }
}
