package sk.konstiak.frontend.frontendvaadin;

import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableFunction;
import sk.konstiak.model.Advertisement;

public class AdvertisementsList extends LoadMoreList<Advertisement> {

    public AdvertisementsList(ListingService<Advertisement> listingService) {
        super(listingService,
                new ComponentRenderer<>(
                        (SerializableFunction<Advertisement, AdvertisementView>) AdvertisementView::new));
    }

}
