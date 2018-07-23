package sk.konstiak.parser;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.normalizeSpace;

public abstract class BazaarParser {

    protected String getNormalizedText(HtmlElement element) {
        return normalizeSpace(element.getTextContent());
    }

    protected String getNormalizedHref(HtmlElement element) {
        return getNormalizedAttribute(element, "href");
    }

    protected String getNormalizedDataSource(HtmlElement element) {
        return getNormalizedAttribute(element, "data-src");
    }

    protected String getNormalizedAttribute(HtmlElement element, String attributeName) {
        return normalizeSpace(element.getAttribute(attributeName));
    }

    protected Double parseAmount(String text) {
        String cleanedText = text.replaceAll("[^\\d.,]+", "").replaceAll(",", ".");
        return NumberUtils.isParsable(cleanedText) ? NumberUtils.toDouble(cleanedText) : null;
    }

    protected <T extends HtmlElement> Optional<T> getFirstByXPath(HtmlElement element, String xpath) {
        return Optional.ofNullable(element.getFirstByXPath(xpath));
    }
}
