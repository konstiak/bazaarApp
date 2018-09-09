package sk.konstiak.frontend.webapp;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.commons.lang3.StringUtils;
import sk.konstiak.model.Advertisement;

import java.util.Optional;

public class AdvertisementView extends HorizontalLayout {

    public AdvertisementView(Advertisement advertisement) {
        VerticalLayout textPart = new VerticalLayout();
        textPart.add(new H3(advertisement.getTitle()),
                new Paragraph(advertisement.getDescription()));
        Optional.ofNullable(advertisement.getPrice()).ifPresent(p -> textPart.add(new H3(p.toString())));
        textPart.add(new Anchor(advertisement.getLink(), "Link"));

        Image image = new Image(advertisement.getImageLink(), StringUtils.join(advertisement.getTitle(), " image"));
        image.setClassName("advertisement-image");

        add(image, textPart);

        this.setWidth("50%");
    }
}
