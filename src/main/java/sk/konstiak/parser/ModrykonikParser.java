package sk.konstiak.parser;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Component;
import sk.konstiak.model.Advertisement;
import sk.konstiak.model.AdvertisementsPage;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.join;

@Component
public class ModrykonikParser extends BazaarParser {

    public AdvertisementsPage parsePage(HtmlPage page) {
        AdvertisementsPage advertisementsPage = new AdvertisementsPage();

        advertisementsPage.setAdvertisements(parseList(page));

        return advertisementsPage;
    }

    private List<Advertisement> parseList(HtmlPage page) {
        List<HtmlDivision> advertisementDivs = page.getByXPath("//div[contains(@class, 'grid-item ')]");
        return advertisementDivs.stream().map(this::parseAdvertisement).collect(Collectors.toList());
    }

    private Advertisement parseAdvertisement(HtmlDivision advertisementDiv) {
        Advertisement.AdvertisementBuilder builder = Advertisement.builder();

        getFirstByXPath(advertisementDiv, "./a")
                .ifPresent(d -> builder.link(join("https://www.modrykonik.sk", getNormalizedHref(d))));
        getFirstByXPath(advertisementDiv, ".//div[@class = 'title']")
                .ifPresent(d -> builder.title(getNormalizedText(d)));
        getFirstByXPath(advertisementDiv, ".//div[@class = 'desc']")
                .ifPresent(d -> builder.description(getNormalizedText(d)));
        getFirstByXPath(advertisementDiv, ".//a[@class = 'username']/span")
                .ifPresent(d -> builder.author(getNormalizedText(d)));
        getFirstByXPath(advertisementDiv, ".//div[@class = 'row1']/div[@class = 'breadcrumb']/span[3]")
                .ifPresent(d -> builder.location(getNormalizedText(d)));
        getFirstByXPath(advertisementDiv, ".//div[@class = 'photo-box']/img")
                .ifPresent(d -> builder.imageLink(getNormalizedDataSource(d)));
        getFirstByXPath(advertisementDiv, ".//span[@class = 'price-old']")
                .ifPresent(d -> builder.oldPrice(parseAmount(getNormalizedText(d))));
        getFirstByXPath(advertisementDiv, ".//span[@class = 'price']")
                .ifPresent(d -> builder.price(parseAmount(getNormalizedText(d))));

        return builder.build();
    }

}

