package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchBarViewModel {
    private final Publisher publisher;
    private final TourService model;

    private final StringProperty searchText = new SimpleStringProperty("");

    // TODO: Implement (x) clear button for text

    public SearchBarViewModel(Publisher publisher, TourService model) {
        this.publisher = publisher;
        this.model = model;
    }

    public void search() {
        publisher.publish(Event.SEARCH_TERM_SEARCHED, searchText.get());
    }

    public StringProperty searchTextProperty() {
        return searchText;
    }

    public String getSearchText() {
        return searchText.get();
    }
}
