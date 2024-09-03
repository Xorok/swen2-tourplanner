package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels.SearchBarViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("FieldCanBeLocal")
class SearchBarViewModelTest {

    private SearchBarViewModel viewModel;
    private Publisher publisher;

    @BeforeEach
    public void beforeEach() {
        publisher = new Publisher();
        viewModel = new SearchBarViewModel(publisher);
    }

    @Test
    public void should_disableSearch_when_noSearchText() {
        viewModel.searchTextProperty().set("");

        assertTrue(viewModel.isSearchDisabled());
    }

    @Test
    public void should_enableSearch_when_searchTerm() {
        viewModel.searchTextProperty().set("Hello World");

        assertFalse(viewModel.isSearchDisabled());
    }

    @Test
    public void should_process_global_search_event() {
        publisher.publish(Event.SEARCH_TERM_SEARCHED, "Test");

        assertEquals("Test", viewModel.getSearchText());
    }
}