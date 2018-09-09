package sk.konstiak.frontend.webapp;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
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

    private final ListBox<AdvertisementView> advertisementsList = new ListBox<>();

    public MainView(CrawlerService crawlerService, ListingService listingService) {
        this.add(new SearchPanel(event -> search(crawlerService, listingService, getSearchPanel(event))));
        this.add(advertisementsList);
        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private SearchPanel getSearchPanel(ClickEvent<Button> event) {
        Optional<Component> searchPanel = event.getSource().getParent();
        return searchPanel.isPresent() && searchPanel.get() instanceof SearchPanel ? (SearchPanel) searchPanel.get() : null;
    }

    private void search(CrawlerService crawlerService, ListingService listingService, SearchPanel searchPanel) {
        if (searchPanel != null) {
            advertisementsList.removeAll();
            Flux<Advertisement> advertisementFlux = crawlerService.simpleSearch(searchPanel.getSearchString());

            listingService.retrieveAdvertisements(advertisementFlux,
                    this::advertisementFound,
                    this::crawlingFinished);
        }
    }

    private void crawlingFinished() {
        this.getUI().ifPresent(ui -> ui.access(() -> Notification.show("Crawling finished!")));
    }

    private void advertisementFound(Advertisement advertisement) {
        this.getUI().ifPresent(ui -> ui.access(() -> advertisementsList.add(new AdvertisementView(advertisement))));
    }

}
