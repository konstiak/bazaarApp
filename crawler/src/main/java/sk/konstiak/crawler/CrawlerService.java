package sk.konstiak.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import sk.konstiak.model.Advertisement;

import java.util.List;

@Service
public class CrawlerService {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

    private final List<BazaarCrawler> bazaarCrawlers;

    @Autowired
    public CrawlerService(List<BazaarCrawler> bazaarCrawlers) {
        this.bazaarCrawlers = bazaarCrawlers;
    }

    public Flux<Advertisement> simpleSearch(String searchString) {
        return Flux.create(sink -> bazaarCrawlers.forEach(c -> c.crawl(searchString, sink)));
    }
}
