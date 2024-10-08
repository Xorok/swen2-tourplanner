package at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts.Point;
import at.tiefenbrunner.swen2semesterprojekt.data.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.data.service.route.OrsProfile;
import at.tiefenbrunner.swen2semesterprojekt.data.service.route.OrsRouteService;
import at.tiefenbrunner.swen2semesterprojekt.data.service.route.RouteResult;
import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.presentation.TourModel;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class TourDetailsViewModel {
    private final Publisher publisher;
    private final TourService tourService;
    private final OrsRouteService routeService;

    private Tour tour;
    private final TourModel tourModel;

    private final BooleanProperty saveDisabled = new SimpleBooleanProperty(true);
    private final BooleanProperty exportDisabled = new SimpleBooleanProperty(true);

    public TourDetailsViewModel(Publisher publisher, TourService tourService, OrsRouteService routeService) {
        this.publisher = publisher;
        this.tourService = tourService;
        this.routeService = routeService;
        this.tour = new Tour();
        this.tourModel = new TourModel();

        setupBindings();
        setupEvents();
    }

    private void setupBindings() {
        // if any tour form fields are empty, disable save button
        InvalidationListener saveBtnlistener = observable -> saveDisabled.set(formFieldIsEmpty());
        this.tourModel.nameProperty().addListener(saveBtnlistener);
        this.tourModel.descriptionProperty().addListener(saveBtnlistener);
        this.tourModel.fromXProperty().addListener(saveBtnlistener);
        this.tourModel.fromYProperty().addListener(saveBtnlistener);
        this.tourModel.toXProperty().addListener(saveBtnlistener);
        this.tourModel.toYProperty().addListener(saveBtnlistener);
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
        publisher.subscribe(Event.TOUR_LOGS_CHANGED, this::showTour);
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
            Optional<Tour> tourOpt = tourService.findTourById(uuid);
            if (tourOpt.isEmpty()) {
                log.error("Couldn't find selected Tour with ID {}", uuidStr);
                // TODO: Handle invalid state
            } else {
                tour = tourOpt.get();
                exportDisabled.set(false);
                tourModel.setModel(tour);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getStackTrace());
        }
    }

    private void resetData() {
        tourModel.setModel(null);
        tour = new Tour();
        exportDisabled.set(true);
    }

    public TourModel getTourModel() {
        return tourModel;
    }

    public String getCoordinateFormat() {
        return Constants.COORDINATE_FORMAT;
    }

    public void save() {
        if (saveDisabled.get()) {
            return;
        }

        transferModelToTour(tour, tourModel); // Transfer changes from model to Tour instance
        fetchRoute(tour, tourModel);
    }

    public void export() {
        if (exportDisabled.get()) {
            return;
        }

        tourService.generateReport(tour);
    }

    private void transferModelToTour(Tour tour, TourModel tourModel) {
        tour.setName(tourModel.getName());
        tour.setDescription(tourModel.getDescription());
        tour.setTourType(tourModel.getTourType());
        tour.setDistanceM(tourModel.getDistanceM());
        tour.setEstimatedTime(Duration.ofMinutes(tourModel.getEstimatedTimeMin()));
    }

    private void fetchRoute(Tour tour, TourModel tourModel) {
        routeService.getRoute(
                tourModel.getFrom(),
                tourModel.getTo(),
                OrsProfile.mapFrom(tourModel.getTourType()),
                (RouteResult result) -> Platform.runLater(() -> processRouteResult(result, tour)),
                (String errorMsg) -> Platform.runLater(() -> processError(errorMsg))
        );
    }

    private void processRouteResult(RouteResult result, Tour tour) {
        tourModel.setErrorMessage("");

        tour.setEstimatedTime(result.getDuration());
        tour.setDistanceM(result.getDistanceInM());

        tour = tourService.saveTour(tour);
        tourService.saveRoute(result.getRoute(), tour);

        publisher.publish(Event.SEARCH_TERM_SEARCHED, ""); // Refresh results view by showing all
        publisher.publish(Event.TOUR_LIST_SELECTED_TOUR, tour.getId().toString()); // Select newly added tour entry
    }

    private void processError(String errorMsg) {
        tourModel.setErrorMessage(errorMsg);
    }

    public BooleanProperty saveDisabledProperty() {
        return saveDisabled;
    }

    public BooleanProperty exportDisabledProperty() {
        return exportDisabled;
    }

    public boolean getSaveDisabled() {
        return saveDisabled.get();
    }

    public boolean getExportDisabled() {
        return exportDisabled.get();
    }

    public boolean isSaveDisabled() {
        return saveDisabled.get();
    }

    public Tour getTour() {
        return tour;
    }
}
