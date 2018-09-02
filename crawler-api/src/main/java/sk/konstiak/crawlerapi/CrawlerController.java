package sk.konstiak.crawlerapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import sk.konstiak.crawler.CrawlerService;
import sk.konstiak.model.Advertisement;

import java.util.List;

@RestController
public class CrawlerController {

    private final CrawlerService crawlerService;

    @Autowired
    public CrawlerController(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @RequestMapping(value = "/simpleSearch", method = RequestMethod.POST)
    public Flux<Advertisement> simpleSearch(String searchString) {
        return crawlerService.simpleSearch(searchString);
    }
}
