package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts.TourDifficulty;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts.TourType;
import at.tiefenbrunner.swen2semesterprojekt.data.service.ConfigService;
import at.tiefenbrunner.swen2semesterprojekt.data.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels.TourLogDetailsViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("FieldCanBeLocal")
class TourLogDetailsViewModelTest {

    private TourLogDetailsViewModel viewModel;
    private Publisher publisher;

    @Mock
    TourService tourServiceMock;
    @Mock
    ConfigService configService;

    private Tour tour;
    private TourLog tourLog;

    @BeforeEach
    public void beforeEach() {
        publisher = new Publisher();
        viewModel = new TourLogDetailsViewModel(publisher, tourServiceMock, configService);

        tour = new Tour(
                UUID.randomUUID(),
                "Am Weg der Entschleunigung: Aigen-Schlägl - Schwarzenberg 19 km",
                "Ein schöner Weg.",
                TourType.HIKE,
                19000,
                Duration.ofHours(5).plus(6, ChronoUnit.MINUTES),
                null,
                null
        );
        tourLog = new TourLog(
                UUID.randomUUID(),
                tour,
                Instant.now().minus(3, ChronoUnit.DAYS),
                "Luckily there was good weather.",
                tour.getDistanceM() - 20,
                tour.getEstimatedTime().minus(10, ChronoUnit.MINUTES),
                TourDifficulty.MEDIUM,
                75
        );
    }

    @Test
    public void should_set_darkTheme_based_on_config() throws IOException {
        ConfigService realConfigService = new ConfigService("src/test/resources/config-dark-theme.properties");
        TourLogDetailsViewModel viewModel = new TourLogDetailsViewModel(publisher, tourServiceMock, realConfigService);

        assertTrue(viewModel.darkThemeProperty().get());
    }

    @Test
    public void should_set_theme_on_themeChange_event() throws IOException {
        ConfigService realConfigService = new ConfigService("src/test/resources/config-dark-theme.properties");
        TourLogDetailsViewModel viewModel = new TourLogDetailsViewModel(publisher, tourServiceMock, realConfigService);

        @SuppressWarnings("unchecked")
        ChangeListener<Boolean> listener = mock(ChangeListener.class);
        InOrder inOrder = inOrder(listener);

        viewModel.darkThemeProperty().addListener(listener);

        publisher.publish(Event.DARK_THEME, "false");
        publisher.publish(Event.DARK_THEME, "true");

        inOrder.verify(listener).changed(any(ObservableValue.class), eq(true), eq(false));
        inOrder.verify(listener).changed(any(ObservableValue.class), eq(false), eq(true));
    }

    @Test
    public void should_set_lightTheme_based_on_config() throws IOException {
        ConfigService realConfigService = new ConfigService("src/test/resources/config-light-theme.properties");
        TourLogDetailsViewModel viewModel = new TourLogDetailsViewModel(publisher, tourServiceMock, realConfigService);

        assertFalse(viewModel.darkThemeProperty().get());
    }

    @Test
    public void should_disableSave_when_formFieldIsEmpty() {
        assertTrue(viewModel.isSaveDisabled());
        viewModel.getTourLogModel().setComment("Comment");
        assertTrue(viewModel.isSaveDisabled());
        viewModel.getTourLogModel().setDistance(30);
        assertTrue(viewModel.isSaveDisabled());
        viewModel.getTourLogModel().totalTimeProperty().set(20L);
        assertTrue(viewModel.isSaveDisabled());
        viewModel.getTourLogModel().ratingProperty().set(20);
        assertFalse(viewModel.isSaveDisabled());
    }

    @Test
    public void should_get_tourDetails_on_CreateEvent() {
        when(tourServiceMock.findTourById(tour.getId()))
                .thenReturn(Optional.of(tour));

        publisher.publish(Event.TOUR_LOGS_CREATE_LOG, tour.getId().toString());

        verify(tourServiceMock).findTourById(tour.getId());
        assertEquals(tour, viewModel.getTourLog().getTour());
    }

    @Test
    public void should_get_tourDetails_on_EditEvent() {
        when(tourServiceMock.findLogById(tour.getId()))
                .thenReturn(Optional.of(tourLog));

        publisher.publish(Event.TOUR_LOGS_EDIT_LOG, tour.getId().toString());

        verify(tourServiceMock).findLogById(tour.getId());
        assertEquals(tourLog, viewModel.getTourLog());
        assertEquals(tourLog.getComment(), viewModel.getTourLogModel().getComment());
    }
}