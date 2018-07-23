package sk.konstiak.parser;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sk.konstiak.model.Advertisement;
import sk.konstiak.model.AdvertisementsPage;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ModrykonikParserTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModrykonikParserTest.class);

    @Autowired
    public WebClient webClient;

    @Autowired
    public ModrykonikParser parser;

    @Test
    public void parsePage() {
        AdvertisementsPage page = parser.parsePage(getPage("modryKonik.html"));
        assertThat(page).isNotNull();
        assertThat(page.getAdvertisements()).isNotNull();
        assertThat(page.getAdvertisements().size()).isEqualTo(40);

        Advertisement advertisement = page.getAdvertisements().get(0);
        assertThat(advertisement.getAuthor()).isNotNull().isNotBlank();
        assertThat(advertisement.getDescription()).isNotNull().isNotBlank();
        assertThat(advertisement.getTitle()).isNotNull().isNotBlank();
        assertThat(advertisement.getImageLink()).isNotNull().isNotBlank();
        assertThat(advertisement.getLink()).isNotNull().isNotBlank();
        assertThat(advertisement.getLocation()).isNotNull().isNotBlank();
        assertThat(advertisement.getPrice()).isNotNull().isEqualTo(5.5);
    }

    @Ignore
    @Test
    public void parseRealPage() throws IOException {
        AdvertisementsPage page = parser.parsePage(webClient.getPage("https://www.modrykonik.sk/market/?q=gulliver&quality=old%2Cnew%2Chandmade&cleanSort=false&sort=score_new"));
        LOGGER.info(page.toString());
    }

    private HtmlPage getPage(String filename) {
        try {
            return webClient.getPage("file://" + getClass().getClassLoader().getResource(filename).getPath());
        } catch (IOException e) {
            throw new RuntimeException(String.format("Test file %s wasn't found.", filename), e);
        }
    }
}