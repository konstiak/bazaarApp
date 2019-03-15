package sk.konstiak.crawlerapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import sk.konstiak.crawler.CrawlerService;
import sk.konstiak.model.Advertisement;

@RestController
public class CrawlerController {

    private final CrawlerService crawlerService;

    @Autowired
    public CrawlerController(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @PostMapping(value = "/simpleSearch")
    public Flux<Advertisement> simpleSearch(String searchString) {
        return crawlerService.simpleSearch(searchString);
    }
}
