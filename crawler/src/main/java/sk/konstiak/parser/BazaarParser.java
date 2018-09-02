package sk.konstiak.parser;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.konstiak.model.Advertisement;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public abstract class BazaarParser {

    private static final Logger logger = LoggerFactory.getLogger(BazaarParser.class);

    public abstract List<Advertisement> parse(Document document);

    public List<Advertisement> parse(String html) {
        return parse(Jsoup.parse(html));
    }

    public List<Advertisement> parse(File file) {
        try {
            return parse(Jsoup.parse(file, "UTF-8"));
        } catch (IOException e) {
            logger.error("It was not possible to read the file.", e);
        }
        return Collections.emptyList();
    }

    protected Double parseAmount(String text) {
        String cleanedText = text.replaceAll("[^\\d.,]+", "").replaceAll(",", ".");
        return NumberUtils.isParsable(cleanedText) ? NumberUtils.toDouble(cleanedText) : null;
    }


}
