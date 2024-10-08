package at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts.Point;
import at.tiefenbrunner.swen2semesterprojekt.data.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Publisher;
import jakarta.annotation.Nullable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.UUID;

@Log4j2
public class MapViewModel {
    private final Publisher publisher;
    private final TourService model;

    private @Nullable String tourId;

    private final StringProperty scriptToExecute = new SimpleStringProperty();

    public MapViewModel(Publisher publisher, TourService model) {
        this.publisher = publisher;
        this.model = model;

        setupEvents();
    }

    private void setupEvents() {
        // on search event, update terms in list
        publisher.subscribe(Event.TOUR_LIST_SELECTED_TOUR, this::getRoute);
        publisher.subscribe(Event.TOUR_LIST_CREATE_TOUR, (empty) -> resetMap());
        publisher.subscribe(Event.SEARCH_TERM_SEARCHED, (empty) -> resetMap());
        publisher.subscribe(Event.TOUR_LIST_DELETED_TOUR, (delId) -> {
            if (isCurrentTour(delId)) {
                resetMap();
            }
        });
    }

    private void getRoute(String uuidStr) {
        List<Point> route = model.findRouteByTourId(UUID.fromString(uuidStr));
        displayRoute(route);
        this.tourId = uuidStr;
    }

    private void displayRoute(List<Point> route) {
        StringBuilder jsCommand = new StringBuilder("drawRoute([");
        for (int i = 0; i < route.size(); i++) {
            double lng = route.get(i).getX();
            double lat = route.get(i).getY();
            jsCommand.append("[").append(lat).append(",").append(lng).append("]");
            if (i < route.size() - 1) {
                jsCommand.append(",");
            }
        }
        jsCommand.append("]);");

        // Execute the JavaScript command to draw the route
        executeScript(jsCommand.toString());
    }

    private boolean isCurrentTour(String id) {
        return (tourId != null && tourId.equals(id));
    }

    private void resetMap() {
        // Execute the JavaScript command to reset the map
        executeScript("resetMap();");
        tourId = null;
    }

    public StringProperty scriptToExecuteProperty() {
        return scriptToExecute;
    }

    public void executeScript(String script) {
        scriptToExecute.set(script);
    }

    public String getTourId() {
        return tourId;
    }

    public String getScriptToExecute() {
        return scriptToExecute.get();
    }
}
