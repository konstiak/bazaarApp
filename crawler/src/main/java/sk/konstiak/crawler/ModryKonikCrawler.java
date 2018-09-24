package sk.konstiak.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sk.konstiak.parser.ModryKonikParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
public class ModryKonikCrawler extends BazaarCrawler {

    private static final Logger logger = LoggerFactory.getLogger(ModryKonikCrawler.class);

    @Value("${crawler.modrykonik.url:https://www.modrykonik.sk/market/?}")
    private String baseUrl;

    @Autowired
    public ModryKonikCrawler(ModryKonikParser parser) {
        super(parser);
    }

    @Override
    protected String getBaseUrl() {
        return baseUrl;
    }

    @Override
    protected String getSimpleSearchUrl(String searchString) {
        try {
            return String.join("&",
                    baseUrl,
                    "q=" + URLEncoder.encode(searchString, "UTF-8"),
                    "quality=old");
        } catch (UnsupportedEncodingException e) {
            logger.error("Search string is not valid.");
        }
        return "";
    }

}
