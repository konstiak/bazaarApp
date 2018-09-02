package sk.konstiak.crawler;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Ignore
class CrawlerServiceTest {

    @Autowired
    private CrawlerService crawlerService;

    @Test
    void simpleSearch() {
        crawlerService.simpleSearch("britax").subscribe(c -> System.out.println(c.toString()));
        assert true;
    }
}