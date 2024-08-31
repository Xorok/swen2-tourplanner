package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Point;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.presentation.TourModel;
import com.sun.jdi.InvalidTypeException;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.extern.log4j.Log4j2;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class TourDetailsViewModel {
    private final Publisher publisher;
    private final TourService model;

    private Tour tour;
    private final TourModel tourModel;

    private final BooleanProperty saveDisabled = new SimpleBooleanProperty(true);

    private final NumberFormat coordinateFormat = new DecimalFormat("##0.000000");

    public TourDetailsViewModel(Publisher publisher, TourService model) {
        this.publisher = publisher;
        this.model = model;
        this.tour = new Tour();
        this.tourModel = new TourModel();

        setupBindings();
        setupEvents();
    }

    private void setupBindings() {
        // if any tour form fields are empty, disable save button
        InvalidationListener listener = observable -> saveDisabled.set(formFieldIsEmpty());
        this.tourModel.nameProperty().addListener(listener);
        this.tourModel.descriptionProperty().addListener(listener);
        this.tourModel.fromXProperty().addListener(listener);
        this.tourModel.fromYProperty().addListener(listener);
        this.tourModel.toXProperty().addListener(listener);
        this.tourModel.toYProperty().addListener(listener);
    }

    private boolean formFieldIsEmpty() {
        return tourModel.getName() == null || tourModel.getName().isBlank() ||
                tourModel.getDescription() == null || tourModel.getDescription().isBlank() ||
                tourModel.getFrom() == null || tourModel.getFrom().equals(new Point()) ||
                tourModel.getTo() == null || tourModel.getTo().equals(new Point());
    }

    private void setupEvents() {
        // on search event, update terms in list
        publisher.subscribe(Event.TOUR_LIST_SELECTED_TOUR, this::showTour);
        publisher.subscribe(Event.TOUR_LIST_CREATE_TOUR, (empty) -> resetData());
        publisher.subscribe(Event.TOUR_LIST_DELETED_TOUR, (delId) -> {
            if (isCurrentTour(delId)) {
                resetData();
            }
        });
        publisher.subscribe(Event.SEARCH_TERM_SEARCHED, (term) -> resetData());
    }

    private boolean isCurrentTour(String id) {
        return (tour.getId() != null && tour.getId().toString().equals(id));
    }

    private void showTour(String uuidStr) {
        try {
            UUID uuid = UUID.fromString(uuidStr);
            Optional<Tour> tourOpt = model.findTourById(uuid);
            if (tourOpt.isEmpty()) {
                log.error("Couldn't find selected Tour with ID {}", uuidStr);
                // TODO: Handle invalid state
            } else {
                tour = tourOpt.get();
                tourModel.setModel(tour);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getStackTrace());
        }
    }

    private void resetData() {
        tourModel.setModel(null);
        tour = new Tour();
    }

    public TourModel getTourModel() {
        return tourModel;
    }

    public NumberFormat getCoordinateFormat() {
        return coordinateFormat;
    }

    public void save() {
        if (saveDisabled.get()) {
            return;
        }

        try {
            tourModel.transferDataToTour(tour); // Transfer changes from model to Tour instance
            Tour savedTour = model.saveTour(tour);
            publisher.publish(Event.SEARCH_TERM_SEARCHED, ""); // Refresh results view by showing all
            publisher.publish(Event.TOUR_LIST_SELECTED_TOUR, savedTour.getId().toString()); // Select newly added tour entry
        } catch (InvalidTypeException e) {
            // TODO: Define more errors in tourModel
            log.error(e.getStackTrace());
        }
    }

    public BooleanProperty saveDisabledProperty() {
        return saveDisabled;
    }

    public boolean getSaveDisabled() {
        return saveDisabled.get();
    }

    public boolean isSaveDisabled() {
        return saveDisabled.get();
    }
}
