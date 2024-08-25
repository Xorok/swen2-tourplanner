package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import jakarta.annotation.Nullable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;
import java.util.UUID;

@Log4j2
public class TourLogsViewModel {
    private final Publisher publisher;
    private final TourService model;

    private @Nullable String tourId;

    private final ObservableList<TourLog> logs = FXCollections.observableArrayList();

    private final BooleanProperty crudDisabled = new SimpleBooleanProperty(true);

    public TourLogsViewModel(Publisher publisher, TourService model) {
        this.publisher = publisher;
        this.model = model;

        setupEvents();
    }

    private void setupEvents() {
        // on search event, update terms in list
        publisher.subscribe(Event.TOUR_LIST_SELECTED_TOUR, this::showLogs);
        publisher.subscribe(Event.TOUR_LIST_CREATE_TOUR, (empty) -> resetData());
        publisher.subscribe(Event.TOUR_LIST_DELETED_TOUR, (delId) -> {
            if (isCurrentTour(delId)) {
                resetData();
            }
        });
        publisher.subscribe(Event.SEARCH_TERM_SEARCHED, (term) -> resetData());
        publisher.subscribe(Event.TOUR_LOGS_CHANGED, (changedTourId) -> {
            if (isCurrentTour(changedTourId)) {
                showLogs(changedTourId);
            }
        });
    }

    private boolean isCurrentTour(String id) {
        return (tourId != null && tourId.equals(id));
    }

    private void showLogs(String id) {
        this.tourId = id;
        logs.setAll(model.findAllLogsByTourId(UUID.fromString(id)));
        crudDisabled.set(false);
    }

    private void resetData() {
        tourId = null;
        logs.clear();
        crudDisabled.set(true);
    }

    public void createTourLog() {
        if (tourId != null) {
            publisher.publish(Event.TOUR_LOGS_CREATE_LOG, tourId);
        }
    }

    public void editTourLog(TourLog tourLog) {
        publisher.publish(Event.TOUR_LOGS_EDIT_LOG, tourLog.getId().toString());
    }

    public void deleteTourLog(TourLog tourLog) {
        Optional<TourLog> tourLogOpt = model.findLogById(tourLog.getId());
        if (tourLogOpt.isEmpty()) {
            log.error("Couldn't find selected Tour Log with ID {} to delete!", tourLog.getId().toString());
            // TODO: Handle invalid state
        } else {
            model.deleteLog(tourLogOpt.get().getId());
            publisher.publish(Event.TOUR_LOGS_CHANGED, tourLog.getTour().getId().toString());
        }
    }

    public ObservableList<TourLog> getLogs() {
        return logs;
    }

    public BooleanProperty crudDisabledProperty() {
        return crudDisabled;
    }
}
