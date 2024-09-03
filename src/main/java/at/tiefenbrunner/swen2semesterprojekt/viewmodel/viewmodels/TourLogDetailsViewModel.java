package at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.data.service.ConfigService;
import at.tiefenbrunner.swen2semesterprojekt.data.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.presentation.TourLogModel;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;
import java.util.UUID;

@Log4j2
public class TourLogDetailsViewModel {
    private final Publisher publisher;
    private final TourService model;
    private final ConfigService configService;

    private TourLog tourLog;
    private final TourLogModel tourLogModel;

    private final BooleanProperty saveDisabled = new SimpleBooleanProperty(true);
    private BooleanProperty darkTheme;

    public TourLogDetailsViewModel(Publisher publisher, TourService model, ConfigService configService) {
        this.publisher = publisher;
        this.model = model;
        this.configService = configService;
        this.tourLog = new TourLog();
        this.tourLogModel = new TourLogModel();

        setupTheme();
        setupBindings();
        setupEvents();
    }

    private void setupTheme() {
        darkTheme = new SimpleBooleanProperty(
                Boolean.parseBoolean(configService.getConfigValue("app.dark-theme"))
        );
    }

    private void setupBindings() {
        // if any tour log form fields are empty, disable save button
        InvalidationListener listener = observable -> saveDisabled.set(formFieldIsEmpty());
        this.tourLogModel.commentProperty().addListener(listener);
        this.tourLogModel.distanceProperty().addListener(listener);
        this.tourLogModel.totalTimeProperty().addListener(listener);
        this.tourLogModel.ratingProperty().addListener(listener);
        this.tourLogModel.tourDifficultyProperty().addListener(listener);
    }

    private boolean formFieldIsEmpty() {
        return tourLogModel.getComment() == null || tourLogModel.getComment().isBlank() ||
                tourLogModel.getDistance() == 0 ||
                tourLogModel.getTotalTime() == 0 ||
                tourLogModel.getRating() == 0;
    }

    private void setupEvents() {
        publisher.subscribe(Event.TOUR_LOGS_CREATE_LOG, this::createNewTourLog);
        publisher.subscribe(Event.TOUR_LOGS_EDIT_LOG, this::showTourLog);
        publisher.subscribe(Event.DARK_THEME, (darkTheme) -> setDarkTheme(Boolean.parseBoolean(darkTheme)));
    }

    private void createNewTourLog(String tourId) {
        try {
            UUID uuid = UUID.fromString(tourId);
            Optional<Tour> tourOpt = model.findTourById(uuid);
            if (tourOpt.isEmpty()) {
                log.error("Couldn't find selected Tour with ID {} to create a new Tour Log!", tourId);
                // TODO: Handle invalid state
            } else {
                resetData();
                tourLog.setTour(tourOpt.get());
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getStackTrace());
        }
    }

    private void showTourLog(String logId) {
        try {
            UUID uuid = UUID.fromString(logId);
            Optional<TourLog> tourLogOpt = model.findLogById(uuid);
            if (tourLogOpt.isEmpty()) {
                log.error("Couldn't find selected Tour Log with ID {} to edit!", logId);
                // TODO: Handle invalid state
            } else {
                tourLog = tourLogOpt.get();
                tourLogModel.setModel(tourLog);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getStackTrace());
        }
    }

    private void setDarkTheme(boolean darkThemeEnabled) {
        darkTheme.set(darkThemeEnabled);
    }

    private void resetData() {
        tourLogModel.setModel(null);
        tourLog = new TourLog();
    }

    public TourLogModel getTourLogModel() {
        return tourLogModel;
    }

    public void save() {
        if (saveDisabled.get()) {
            return;
        }

        tourLogModel.transferDataToTourLog(tourLog); // Transfer changes from model to Tour instance
        tourLog = model.saveLog(tourLog);
        publisher.publish(Event.TOUR_LOGS_CHANGED, tourLog.getTour().getId().toString()); // Refresh logs
    }

    public BooleanProperty saveDisabledProperty() {
        return saveDisabled;
    }

    public BooleanProperty darkThemeProperty() {
        return darkTheme;
    }

    public boolean getSaveDisabled() {
        return saveDisabled.get();
    }

    public boolean isSaveDisabled() {
        return saveDisabled.get();
    }

    public TourLog getTourLog() {
        return tourLog;
    }
}
