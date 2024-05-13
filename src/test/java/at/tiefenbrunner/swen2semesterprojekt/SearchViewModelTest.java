package at.tiefenbrunner.swen2semesterprojekt;

import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SearchViewModelTest {

    private SearchViewModel viewModel;

    private Publisher publisher;

    @BeforeEach
    public void beforeEach() {
        publisher = new Publisher();
        viewModel = new SearchViewModel(publisher);
    }

    @Test
    public void should_disableSearch_when_noSearchTerm() {
        viewModel.searchTextProperty().set("");

        assertTrue(viewModel.isSearchDisabled());
    }

    @Test
    public void should_enableSearch_when_searchTerm() {
        viewModel.searchTextProperty().set("Hello World");

        assertFalse(viewModel.isSearchDisabled());
    }

    @Test
    public void should_resetSearchTerm_when_searchPerformed() {
        viewModel.searchTextProperty().set("Hello World");

        viewModel.search();

        assertEquals("", viewModel.getSearchText());
    }

    @Test
    public void should_insertSearchTerm_when_searchTermSelectedEvent() {
        assertEquals("", viewModel.getSearchText());

        publisher.publish(Event.SEARCH_TERM_SELECTED, "Vienna");

        assertEquals("Vienna", viewModel.getSearchText());
    }
}