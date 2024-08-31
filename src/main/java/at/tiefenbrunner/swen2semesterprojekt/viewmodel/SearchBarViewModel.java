package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchBarViewModel {
    private final Publisher publisher;
    private final TourService model;

    private final StringProperty searchText = new SimpleStringProperty("");
    private final BooleanProperty searchDisabled = new SimpleBooleanProperty(true);

    // TODO: Implement (x) clear button for text

    public SearchBarViewModel(Publisher publisher, TourService model) {
        this.publisher = publisher;
        this.model = model;

        setupBindings();
        setupEvents();
    }

    private void setupBindings() {
        // if search text is empty, disable search
        this.searchText.addListener(
                observable -> searchDisabled.set(searchText.get() == null || searchText.get().isEmpty())
        );
    }

    private void setupEvents() {
        publisher.subscribe(Event.SEARCH_TERM_SEARCHED, searchText::set); // TODO: Remove once more proper events are added
    }

    public void search() {
        if (searchDisabled.get()) {
            return;
        }

        publisher.publish(Event.SEARCH_TERM_SEARCHED, searchText.get());
    }

    public void showAll() {
        publisher.publish(Event.SEARCH_TERM_SEARCHED, "");
        searchText.set("");
    }

    public StringProperty searchTextProperty() {
        return searchText;
    }

    public String getSearchText() {
        return searchText.get();
    }

    public BooleanProperty searchDisabledProperty() {
        return searchDisabled;
    }

    public boolean getSearchDisabled() {
        return searchDisabled.get();
    }

    public boolean isSearchDisabled() {
        return searchDisabled.get();
    }
}
