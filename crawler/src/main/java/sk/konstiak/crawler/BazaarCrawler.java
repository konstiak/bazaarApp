package sk.konstiak.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.FluxSink;
import sk.konstiak.model.Advertisement;
import sk.konstiak.parser.BazaarParser;

import java.util.regex.Pattern;

public abstract class BazaarCrawler extends WebCrawler {

    private static final Pattern COMMON_FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$");
    private static final Logger logger = LoggerFactory.getLogger(BazaarCrawler.class);
    private final BazaarParser parser;
    private FluxSink<Advertisement> sink;

    public BazaarCrawler(BazaarParser parser) {
        this.parser = parser;
    }

    protected abstract String getBaseUrl();

    protected abstract String getSimpleSearchUrl(String searchString);

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !COMMON_FILTERS.matcher(href).matches()
                && href.startsWith(getBaseUrl())
                && href.contains("?q=");
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        logger.info("Visited URL: " + url);
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            parser.parse(html).forEach(a -> sink.next(a));
        }
    }

    @Override
    public void onBeforeExit() {
        super.onBeforeExit();
        sink.complete();
    }

    public void crawl(String searchString, FluxSink<Advertisement> sink) {
        this.sink = sink;
        CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setCrawlStorageFolder("/tmp/crawlerStorage");
        PageFetcher pageFetcher = new PageFetcher(crawlConfig);
        RobotstxtConfig robotsConfig = new RobotstxtConfig();
        robotsConfig.setUserAgentName("BazaarCrawler");
        robotsConfig.setIgnoreUADiscrimination(true);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotsConfig, pageFetcher);

        try {
            CrawlController controller = new CrawlController(crawlConfig, pageFetcher, robotstxtServer);
            controller.addSeed(getSimpleSearchUrl(searchString));
            controller.start(() -> this, 10);
        } catch (Exception e) {
            logger.warn("Crawler couldn't be initialized.", e);
        }
    }
}
