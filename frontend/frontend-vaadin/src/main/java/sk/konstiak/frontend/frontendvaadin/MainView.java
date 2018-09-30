package sk.konstiak.frontend.frontendvaadin;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import reactor.core.publisher.Flux;
import sk.konstiak.crawler.CrawlerService;
import sk.konstiak.model.Advertisement;

import java.util.Optional;

@Route
@Push
public class MainView extends VerticalLayout {

    private final AdvertisementsList advertisementsList;

    public MainView(CrawlerService crawlerService, ListingService<Advertisement> listingService) {
        this.advertisementsList = new AdvertisementsList(listingService);

        this.add(new SearchPanel(event -> search(crawlerService, getSearchPanel(event))));
        this.add(advertisementsList);
        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private SearchPanel getSearchPanel(ClickEvent<Button> event) {
        Optional<Component> searchPanel = event.getSource().getParent();
        return searchPanel.isPresent() && searchPanel.get() instanceof SearchPanel ? (SearchPanel) searchPanel.get() : null;
    }

    private void search(CrawlerService crawlerService, SearchPanel searchPanel) {
        if (searchPanel != null) {
            advertisementsList.removeAll();
            Flux<Advertisement> advertisementFlux = crawlerService.simpleSearch(searchPanel.getSearchString());

            advertisementsList.displayFlux(advertisementFlux);
        }
    }

}
