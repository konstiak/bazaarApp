package sk.konstiak.service;

import com.gargoylesoftware.htmlunit.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.konstiak.model.AdvertisementsPage;
import sk.konstiak.parser.ModrykonikParser;

import java.io.IOException;

@Service
public class ModrykonikLoaderService {

    private final WebClient webClient;
    private final ModrykonikParser parser;

    @Autowired
    public ModrykonikLoaderService(WebClient webClient, ModrykonikParser parser) {
        this.webClient = webClient;
        this.parser = parser;
    }

    public AdvertisementsPage loadPage(String url) {
        try {
            return parser.parsePage(webClient.getPage(url));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new AdvertisementsPage();
    }
}
