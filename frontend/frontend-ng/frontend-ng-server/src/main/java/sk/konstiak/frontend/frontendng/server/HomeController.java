package sk.konstiak.frontend.frontendng.server;

import org.springframework.core.task.TaskExecutor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import sk.konstiak.crawler.CrawlerService;
import sk.konstiak.model.Advertisement;

import java.time.Duration;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

@Controller
@RequestMapping("/")
public class HomeController {

    private final TaskExecutor taskExecutor;
    private final CrawlerService crawlerService;

    public HomeController(TaskExecutor taskExecutor, CrawlerService crawlerService) {
        this.taskExecutor = taskExecutor;
        this.crawlerService = crawlerService;
    }

    @GetMapping
    public String home(Model model) {
        return "forward:/index.html";
    }

    @GetMapping(value = "/simpleSearch/{searchString}")
    public Flux<ServerSentEvent<Advertisement>> searchResult(@PathVariable("searchString") String searchString) {
        Flux<Advertisement> advertisementFlux = crawlerService.simpleSearch(searchString);
        return advertisementFlux.map(advertisement -> ServerSentEvent.builder(advertisement).event("advertisement").build());
    }

    @GetMapping(value = "/stub")
    public Flux<ServerSentEvent<Advertisement>> stub() {
        return Flux.range(1, 10).map(integer ->
                ServerSentEvent.builder(random(Advertisement.class)).event("advertisement").build())
                .delayElements(Duration.ofSeconds(1)).log("ADVERTISEMENT");
    }
}
