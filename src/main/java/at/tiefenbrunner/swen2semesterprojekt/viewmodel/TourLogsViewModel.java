package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import jakarta.annotation.Nullable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.UUID;

public class TourLogsViewModel {
    private final Publisher publisher;
    private final TourService model;

    private @Nullable String tourId;

    private final ObservableList<TourLog> logs = FXCollections.observableArrayList();

    public TourLogsViewModel(Publisher publisher, TourService model) {
        this.publisher = publisher;
        this.model = model;

        setupEvents();
    }

    private void setupEvents() {
        // on search event, update terms in list
        publisher.subscribe(Event.TOUR_LIST_TOUR_SELECTED, this::showLogs);
        publisher.subscribe(Event.TOUR_LIST_ADD_NEW_TOUR, (empty) -> resetData());
        publisher.subscribe(Event.TOUR_LIST_DELETE_TOUR, (delId) -> {
            if (isCurrentTour(delId)) {
                resetData();
            }
        });
    }

    private boolean isCurrentTour(String id) {
        return (tourId != null && tourId.equals(id));
    }

    private void showLogs(String id) {
        this.tourId = id;
        logs.setAll(model.findAllLogsByTourId(UUID.fromString(id)));
    }

    private void resetData() {
        tourId = null;
        logs.clear();
    }

    public ObservableList<TourLog> getLogs() {
        return logs;
    }
}
