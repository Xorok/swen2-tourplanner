package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.TourType;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.service.route.OrsRouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("FieldCanBeLocal")
class TourDetailsViewModelTest {

    private TourDetailsViewModel viewModel;
    private Publisher publisher;

    @Mock
    TourService tourServiceMock;
    @Mock
    OrsRouteService routeServiceMock;

    private Tour tour;

    @BeforeEach
    public void beforeEach() {
        publisher = new Publisher();
        viewModel = new TourDetailsViewModel(publisher, tourServiceMock, routeServiceMock);

        tour = new Tour(
                UUID.randomUUID(),
                "Name",
                "Desc",
                TourType.VACATION,
                342760,
                Duration.ofHours(1).plus(20, ChronoUnit.MINUTES),
                null,
                null
        );
    }

    @Test
    public void should_disableSave_when_formFieldIsEmpty() {
        assertTrue(viewModel.isSaveDisabled());
        viewModel.getTourModel().setName("Name");
        assertTrue(viewModel.isSaveDisabled());
        viewModel.getTourModel().setDescription("Description");
        assertTrue(viewModel.isSaveDisabled());
        viewModel.getTourModel().fromXProperty().set(47.000);
        assertTrue(viewModel.isSaveDisabled());
        viewModel.getTourModel().fromYProperty().set(47.000);
        assertTrue(viewModel.isSaveDisabled());
        viewModel.getTourModel().toXProperty().set(47.000);
        assertTrue(viewModel.isSaveDisabled());
        viewModel.getTourModel().toYProperty().set(47.000);
        assertFalse(viewModel.isSaveDisabled());
    }

    @Test
    public void should_show_tourDetails_on_SelectEvent() {
        when(tourServiceMock.findTourById(tour.getId()))
                .thenReturn(Optional.of(tour));

        publisher.publish(Event.TOUR_LIST_SELECTED_TOUR, tour.getId().toString());

        verify(tourServiceMock).findTourById(tour.getId());
        assertEquals(tour, viewModel.getTour());
        assertEquals(tour.getName(), viewModel.getTourModel().getName());
    }

    @Test
    public void should_reset_tourDetails_on_CreateEvent() {
        when(tourServiceMock.findTourById(tour.getId()))
                .thenReturn(Optional.of(tour));
        publisher.publish(Event.TOUR_LIST_SELECTED_TOUR, tour.getId().toString());
        assertEquals(tour, viewModel.getTour());
        assertEquals(tour.getName(), viewModel.getTourModel().getName());
        assertFalse(viewModel.getExportDisabled());

        publisher.publish(Event.TOUR_LIST_CREATE_TOUR, "");

        assertNotEquals(tour, viewModel.getTour());
        assertNotEquals(tour.getName(), viewModel.getTourModel().getName());
        assertTrue(viewModel.getExportDisabled());
    }

    @Test
    public void should_reset_tourDetails_on_gotDeleted_Event() {
        when(tourServiceMock.findTourById(tour.getId()))
                .thenReturn(Optional.of(tour));
        publisher.publish(Event.TOUR_LIST_SELECTED_TOUR, tour.getId().toString());
        assertEquals(tour, viewModel.getTour());
        assertEquals(tour.getName(), viewModel.getTourModel().getName());
        assertFalse(viewModel.getExportDisabled());

        publisher.publish(Event.TOUR_LIST_DELETED_TOUR, tour.getId().toString());

        assertNotEquals(tour, viewModel.getTour());
        assertNotEquals(tour.getName(), viewModel.getTourModel().getName());
        assertTrue(viewModel.getExportDisabled());
    }

    @Test
    public void should_resetMap_on_SearchEvent() {
        when(tourServiceMock.findTourById(tour.getId()))
                .thenReturn(Optional.of(tour));
        publisher.publish(Event.TOUR_LIST_SELECTED_TOUR, tour.getId().toString());
        assertEquals(tour, viewModel.getTour());
        assertEquals(tour.getName(), viewModel.getTourModel().getName());
        assertFalse(viewModel.getExportDisabled());

        publisher.publish(Event.SEARCH_TERM_SEARCHED, "");

        assertNotEquals(tour, viewModel.getTour());
        assertNotEquals(tour.getName(), viewModel.getTourModel().getName());
        assertTrue(viewModel.getExportDisabled());
    }
}