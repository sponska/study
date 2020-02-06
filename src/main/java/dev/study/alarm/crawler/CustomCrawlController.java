package dev.study.alarm.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CustomCrawlController extends CrawlController {

    public CustomCrawlController(CrawlConfig config, PageFetcher pageFetcher, RobotstxtServer robotstxtServer) throws Exception {
        super(config, pageFetcher, robotstxtServer);
    }

    @Override
    public void addSeed(String pageUrl, int docId) {
        if (pageUrl == null) {
            log.error("Invalid seed URL: {}", pageUrl);
        } else {
            if (docId < 0) {
                docId = docIdServer.getDocId(pageUrl);
                if (docId > 0) {
                    log.trace("This URL is already seen.");
                    return;
                }
                docId = docIdServer.getNewDocID(pageUrl);
            } else {
                try {
                    docIdServer.addUrlAndDocId(pageUrl, docId);
                } catch (Exception e) {
                    log.error("Could not add seed: {}", e.getMessage());
                }
            }

            WebURL webUrl = new WebURL();
            webUrl.setURL(pageUrl);
            webUrl.setDocid(docId);
            webUrl.setDepth((short) 0);
            if (robotstxtServer.allows(webUrl)) {
                frontier.schedule(webUrl);
            } else {
                // using the WARN level here, as the user specifically asked to add this seed
                log.warn("Robots.txt does not allow this seed: {}", pageUrl);
            }
        }
    }
}
