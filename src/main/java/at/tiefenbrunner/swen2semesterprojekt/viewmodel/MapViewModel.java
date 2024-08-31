package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.service.route.OrsProfile;
import at.tiefenbrunner.swen2semesterprojekt.service.route.OrsRouteService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Optional;
import java.util.UUID;

@Log4j2
public class MapViewModel {
    private final Publisher publisher;
    private final OrsRouteService routeService;
    private final TourService model;

    private final StringProperty scriptToExecute = new SimpleStringProperty();

    public MapViewModel(Publisher publisher, TourService model, OrsRouteService routeService) {
        this.publisher = publisher;
        this.model = model;
        this.routeService = routeService;

        setupEvents();
    }

    private void setupEvents() {
        // on search event, update terms in list
        publisher.subscribe(Event.TOUR_LIST_SELECTED_TOUR, this::getRoute);
    }

    public void getRoute(String uuidStr) {
        try {
            UUID uuid = UUID.fromString(uuidStr);
            Optional<Tour> tourOpt = model.findTourById(uuid);
            if (tourOpt.isEmpty()) {
                log.error("Couldn't find selected Tour with ID {}", uuidStr);
                // TODO: Handle invalid state
            } else {
                Tour tour = tourOpt.get();
                routeService.getRoute(
                        tour.getFrom(),
                        tour.getTo(),
                        OrsProfile.mapFrom(tour.getTourType()),
                        this::displayRoute
                );
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getStackTrace());
        }
    }

    private void displayRoute(JSONObject route) {
        JSONArray coordinates = route.getJSONArray("features")
                .getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONArray("coordinates");

        StringBuilder jsCommand = new StringBuilder("drawRoute([");
        for (int i = 0; i < coordinates.length(); i++) {
            JSONArray coord = coordinates.getJSONArray(i);
            double lng = coord.getDouble(0);
            double lat = coord.getDouble(1);
            jsCommand.append("[").append(lat).append(",").append(lng).append("]");
            if (i < coordinates.length() - 1) {
                jsCommand.append(",");
            }
        }
        jsCommand.append("]);");

        Platform.runLater(() -> {
            // Execute the JavaScript command to draw the route
            executeScript(jsCommand.toString());
        });
    }

    public StringProperty scriptToExecuteProperty() {
        return scriptToExecute;
    }

    public void executeScript(String script) {
        scriptToExecute.set(script);
    }
}
