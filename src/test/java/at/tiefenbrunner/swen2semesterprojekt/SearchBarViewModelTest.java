package at.tiefenbrunner.swen2semesterprojekt;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.repository.TourMemoryRepository;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.SearchBarViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("FieldCanBeLocal")
class SearchBarViewModelTest {

    private SearchBarViewModel viewModel;

    private Publisher publisher;
    private TourService tourService;
    private TourMemoryRepository tourMemoryRepository;

    @BeforeEach
    public void beforeEach() {
        publisher = new Publisher();
        // TODO: Consider mocks, move TourMemoryRepository with test data into test package
        tourMemoryRepository = new TourMemoryRepository();
        tourService = new TourService(tourMemoryRepository);

        viewModel = new SearchBarViewModel(publisher, tourService);
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

        publisher.publish(Event.TOUR_LIST_TOUR_SELECTED, "ff9ef350-3ff6-4391-9758-b81fa7f57095");

        assertEquals("Bike Trail - Saalach Valley", viewModel.getSearchText());
    }
}