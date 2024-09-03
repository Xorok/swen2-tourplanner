package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.Point;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.TourType;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("FieldCanBeLocal")
class MapViewModelTest {

    private MapViewModel viewModel;
    private Publisher publisher;

    @Mock
    TourService tourServiceMock;

    private Tour tour;

    @BeforeEach
    public void beforeEach() {
        publisher = new Publisher();
        viewModel = new MapViewModel(publisher, tourServiceMock);

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
    public void should_show_routeMap_on_SelectEvent() {
        List<Point> route = List.of(
                new Point(-0.1278, 51.5074),  // London
                new Point(2.3522, 48.8566)   // Paris
        );
        when(tourServiceMock.findRouteByTourId(tour.getId()))
                .thenReturn(route);

        publisher.publish(Event.TOUR_LIST_SELECTED_TOUR, tour.getId().toString());

        verify(tourServiceMock).findRouteByTourId(tour.getId());
        assertEquals(tour.getId().toString(), viewModel.getTourId());
        assertEquals(
                "drawRoute([[51.5074,-0.1278],[48.8566,2.3522]]);",
                viewModel.getScriptToExecute()
        );
    }

    @Test
    public void should_resetMap_on_CreateEvent() {
        String script = "resetMap();";
        publisher.publish(Event.TOUR_LIST_SELECTED_TOUR, tour.getId().toString());
        assertNotNull(viewModel.getTourId());
        assertNotEquals(script, viewModel.getScriptToExecute());

        publisher.publish(Event.TOUR_LIST_CREATE_TOUR, "");

        assertNull(viewModel.getTourId());
        assertEquals(
                script,
                viewModel.getScriptToExecute()
        );
    }

    @Test
    public void should_resetMap_on_SearchEvent() {
        String script = "resetMap();";
        publisher.publish(Event.TOUR_LIST_SELECTED_TOUR, tour.getId().toString());
        assertNotNull(viewModel.getTourId());
        assertNotEquals(script, viewModel.getScriptToExecute());

        publisher.publish(Event.SEARCH_TERM_SEARCHED, "");

        assertNull(viewModel.getTourId());
        assertEquals(
                script,
                viewModel.getScriptToExecute()
        );
    }

    @Test
    public void should_resetMap_on_gotDeleted_Event() {
        String script = "resetMap();";
        publisher.publish(Event.TOUR_LIST_SELECTED_TOUR, tour.getId().toString());
        assertNotNull(viewModel.getTourId());
        assertNotEquals(script, viewModel.getScriptToExecute());

        publisher.publish(Event.TOUR_LIST_DELETED_TOUR, tour.getId().toString());

        assertNull(viewModel.getTourId());
        assertEquals(
                script,
                viewModel.getScriptToExecute()
        );
    }
}