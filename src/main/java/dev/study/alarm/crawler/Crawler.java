package dev.study.alarm.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Set;

@Slf4j
public class Crawler extends WebCrawler {

    @Override
    public void visit(Page page) {
        int docId = page.getWebURL()
                .getDocid();
        String url = page.getWebURL()
                .getURL();
        String domain = page.getWebURL()
                .getDomain();
        String path = page.getWebURL()
                .getPath();
        String subDomain = page.getWebURL()
                .getSubDomain();
        String parentUrl = page.getWebURL()
                .getParentUrl();
        String anchor = page.getWebURL()
                .getAnchor();

        if (page.getParseData() instanceof HtmlParseData) {

            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();


            Document doc = Jsoup.parse(html);

            log.debug(doc.html());

        }

    }
}
