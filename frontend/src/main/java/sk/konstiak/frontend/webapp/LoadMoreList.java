package sk.konstiak.frontend.webapp;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LoadMoreList<T> extends VerticalLayout {

    private final VerticalLayout itemsList = new VerticalLayout();
    private int step = 10;
    private List<T> allItems = new ArrayList<>();

    private final ListingService<T> listingService;

    private final Button loadMoreButton = new Button("Load more", loadMore());
    private final ComponentRenderer<? extends Component, T> componentRenderer;

    public LoadMoreList(ListingService<T> listingService, ComponentRenderer<? extends Component, T> componentRenderer) {
        add(itemsList);
        add(loadMoreButton);
        this.listingService = listingService;
        this.componentRenderer = componentRenderer;
    }

    private ComponentEventListener<ClickEvent<Button>> loadMore() {
        return event -> {
            List<T> nextPage = allItems.stream()
                    .skip(getDisplayedSize())
                    .limit(step)
                    .collect(Collectors.toList());

            addToList(nextPage);
            refreshLoadMoreButton();
        };
    }

    public void displayFlux(Flux<T> itemsFlux) {
        listingService.retrieveItems(itemsFlux, this::nextItem, this::fluxFinished);
    }

    private boolean resultsFromFirstPage() {
        return getDisplayedSize() <= step;
    }

    private void addToList(T... items) {
        addToList(Arrays.asList(items));
    }
    private void addToList(List<T> items) {
        this.getUI().ifPresent(ui -> ui.access(() -> {
            itemsList.add(items.stream().map(componentRenderer::createComponent).toArray(AdvertisementView[]::new));
        }));
    }

    private void refreshLoadMoreButton() {
        int moreAvailable = getAvailableItemsCount() - getDisplayedSize();

        if (moreAvailable > 0) {
            String buttonLabel = String.format("Load (%d) more", moreAvailable);
            this.getUI().ifPresent(ui -> ui.access(() -> loadMoreButton.setText(buttonLabel)));

            if (!loadMoreButton.isEnabled()) {
                this.getUI().ifPresent(ui -> ui.access(() -> loadMoreButton.setEnabled(true)));
            }
        } else {
            loadMoreButton.setText("Load more");
            loadMoreButton.setEnabled(false);
        }
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getDisplayedSize() {
        return itemsList.getComponentCount();
    }

    public int getAvailableItemsCount() {
        return allItems.size();
    }

    private void fluxFinished() {
        // TODO implement notification
//        this.getUI().ifPresent(ui -> ui.access(() -> Notification.show("Crawling finished!")));
    }

    private void nextItem(T item) {
        allItems.add(item);

            if (resultsFromFirstPage()) {
                addToList(item);
            } else {
                refreshLoadMoreButton();
        }
    }

    @Override
    public void removeAll() {
        itemsList.removeAll();
    }
}
