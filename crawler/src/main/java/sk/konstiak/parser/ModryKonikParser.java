package sk.konstiak.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sk.konstiak.model.Advertisement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.join;

@Component
public class ModryKonikParser extends BazaarParser {

    private static final Logger logger = LoggerFactory.getLogger(ModryKonikParser.class);

    @Override
    public List<Advertisement> parse(Document document) {
        Elements advertisementDivs = document.select("div.grid-item");
        return advertisementDivs.stream().map(this::parseAdvertisement).collect(Collectors.toList());
    }

    private Advertisement parseAdvertisement(Element advertisementDiv) {
        Advertisement.AdvertisementBuilder builder = Advertisement.builder();

        selectFirst(advertisementDiv, "a")
                .ifPresent(e -> builder.link(join("https://www.modrykonik.sk", e.attr("href"))));
        selectFirst(advertisementDiv, "div.title")
                .ifPresent(e -> builder.title(e.text()));
        selectFirst(advertisementDiv, "div.desc")
                .ifPresent(e -> builder.description(e.text()));
        selectFirst(advertisementDiv, "a.username > span")
                .ifPresent(e -> builder.author(e.text()));
        selectFirst(advertisementDiv, "div.row1 > div.breadcrumb > span:eq(2)")
                .ifPresent(e -> builder.location(e.text()));
        selectFirst(advertisementDiv, "div.photo-box > img")
                .ifPresent(e -> builder.imageLink(e.attr("data-src")));
        selectFirst(advertisementDiv, "span.price-old")
                .ifPresent(e -> builder.oldPrice(parseAmount(e.text())));
        selectFirst(advertisementDiv, "span.price")
                .ifPresent(e -> builder.price(parseAmount(e.text())));

        return builder.build();
    }

    private Optional<Element> selectFirst(Element element, String cssQuery) {
        return Optional.ofNullable(element.selectFirst(cssQuery));
    }
}
