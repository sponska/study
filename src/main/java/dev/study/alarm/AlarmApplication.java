package dev.study.alarm;

import dev.study.alarm.crawler.CustomCrawlController;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.URLCanonicalizer;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class AlarmApplication {

    private static final String BASE_URL = "http://boardlife.co.kr";
    private static final String BOARD_URL = BASE_URL + "/bbs_list.php?tb=board_used&search_mode=ok&id=&game_id=&happy_board_search_fields%5B%5D=bbs_name&happy_board_search_fields%5B%5D=bbs_review&happy_board_search_fields%5B%5D=bbs_title&happy_board_keyword=%C5%A9%B7%B9%C0%CC%C1%F6+%C5%B8%C0%D3&x=28&y=16";

    public static void main(String[] args) throws Exception {



        /*crawler();*/

        jsoup();

    }

    private static void crawler() throws Exception {
        CrawlConfig config = new CrawlConfig();

        // Set the folder where intermediate crawl data is stored (e.g. list of urls that are extracted from previously
        // fetched pages and need to be crawled later).
        config.setCrawlStorageFolder("/tmp/crawler4j/");

        // Be polite: Make sure that we don't send more than 1 request per second (1000 milliseconds between requests).
        // Otherwise it may overload the target servers.
        config.setPolitenessDelay(1000);


        // You can set the maximum crawl depth here. The default value is -1 for unlimited depth.
        config.setMaxDepthOfCrawling(2);

        // You can set the maximum number of pages to crawl. The default value is -1 for unlimited number of pages.
        config.setMaxPagesToFetch(1000);

        // Should binary data should also be crawled? example: the contents of pdf, or the metadata of images etc
        config.setIncludeBinaryContentInCrawling(false);

        // Do you need to set a proxy? If so, you can use:
        // config.setProxyHost("proxyserver.example.com");
        // config.setProxyPort(8080);

        // If your proxy also needs authentication:
        // config.setProxyUsername(username); config.getProxyPassword(password);

        // This config parameter can be used to set your crawl to be resumable
        // (meaning that you can resume the crawl from a previously
        // interrupted/crashed crawl). Note: if you enable resuming feature and
        // want to start a fresh crawl, you need to delete the contents of
        // rootFolder manually.
        config.setResumableCrawling(false);



        // Set this to true if you want crawling to stop whenever an unexpected error
        // occurs. You'll probably want this set to true when you first start testing
        // your crawler, and then set to false once you're ready to let the crawler run
        // for a long time.

        // Instantiate the controller for this crawl.
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CustomCrawlController(config, pageFetcher, robotstxtServer);




        // For each crawl, you need to add some seed urls. These are the first
        // URLs that are fetched and then the crawler starts following links
        // which are found in these pages
        controller.addSeed("http://boardlife.co.kr/bbs_list.php?tb=board_used&search_mode=ok&id=&game_id=&happy_board_search_fields%5B%5D=bbs_name&happy_board_search_fields%5B%5D=bbs_review&happy_board_search_fields%5B%5D=bbs_title&happy_board_keyword=%C5%A9%B7%B9%C0%CC%C1%F6+%C5%B8%C0%D3&x=22&y=16");

        // Number of threads to use during crawling. Increasing this typically makes crawling faster. But crawling
        // speed depends on many other factors as well. You can experiment with this to figure out what number of
        // threads works best for you.
        int numberOfCrawlers = 8;

        // To demonstrate an example of how you can pass objects to crawlers, we use an AtomicInteger that crawlers
        // increment whenever they see a url which points to an image.
        AtomicInteger numSeenImages = new AtomicInteger();

        // The factory which creates instances of crawlers.
        CrawlController.WebCrawlerFactory<BasicCrawler> factory = () -> new BasicCrawler(numSeenImages);

        // Start the crawl. This is a blocking operation, meaning that your code
        // will reach the line after this only when crawling is finished.

        controller.start(factory, numberOfCrawlers);
    }

    private static void jsoup() throws IOException {
        Connection.Response response = Jsoup.connect(BOARD_URL)
                .method(Connection.Method.GET)
                .execute();

        Document document = response.parse();

        Elements elements = document.select("tr[onmouseover=this.style.backgroundColor='#ffffff']");

        Optional<Element> first = elements.stream()
                .filter(element -> element.select("td img[src=img/판매.png]")
                                           .first() != null)
                .findFirst();

        String title = first
                .map(element -> element.select("a[target=_self]").first())
                .map(element -> element.html()
                        .split("<")[0])
                .orElse("");

        String link = first.map(element -> element.select("a[target=_self]")
                .first())
                .map(element -> BASE_URL + element.attr("href").substring(1))
                .orElse("");

        System.out.println(title);
        System.out.println(link);
    }

}
