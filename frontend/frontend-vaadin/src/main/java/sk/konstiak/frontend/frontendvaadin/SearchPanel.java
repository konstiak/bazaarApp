package sk.konstiak.frontend.frontendvaadin;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class SearchPanel extends HorizontalLayout {

    private final TextField searchStringField = new TextField();

    public SearchPanel(ComponentEventListener<ClickEvent<Button>> searchEventListener) {
        this.add(searchStringField);
        this.add(new Button("Search", searchEventListener));
    }

    public String getSearchString() {
        return searchStringField.getValue();
    }
}
