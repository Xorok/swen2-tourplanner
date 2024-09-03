package at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels;

import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Publisher;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchBarViewModel {
    private final Publisher publisher;

    private final StringProperty searchText = new SimpleStringProperty("");
    private final BooleanProperty searchDisabled = new SimpleBooleanProperty(true);

    // TODO: Implement (x) clear button for text

    public SearchBarViewModel(Publisher publisher) {
        this.publisher = publisher;

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
