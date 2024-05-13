package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.UUID;

public class TourListViewModel {

    private final Publisher publisher;
    private final TourService model;

    private List<Tour> tours;
    private final ObservableList<String> tourNames = FXCollections.observableArrayList();
    private final IntegerProperty selectedTourIndex = new SimpleIntegerProperty();

    public TourListViewModel(
            Publisher publisher,
            TourService model
    ) {
        this.publisher = publisher;
        this.model = model;

        setupEvents();
        showAllTours();
    }

    private void setupEvents() {
        this.selectedTourIndex.addListener(
                observable -> selectTour()
        );

        // on search event, update terms in list
        publisher.subscribe(Event.SEARCH_TERM_SEARCHED, this::queryTours);
        publisher.subscribe(Event.TOUR_LIST_DELETED_TOUR, (deletedTourId) -> queryTours(""));
    }

    public void selectTour() {
        if (selectedTourIndex.get() == -1) {
            return;
        }

        publisher.publish(
                Event.TOUR_LIST_TOUR_SELECTED,
                tours.get(selectedTourIndex.get()).getId().toString()
        );
    }

    private void showAllTours() {
        changeTours(model.findAll());
    }

    public void addNew() {
        publisher.publish(Event.TOUR_LIST_ADD_NEW_TOUR, "");
    }

    public void delete() {
        if (selectedTourIndex.get() == -1) {
            return;
        }

        UUID idToDelete = tours.get(selectedTourIndex.get()).getId();
        publisher.publish(Event.TOUR_LIST_DELETE_TOUR, idToDelete.toString());
        model.delete(idToDelete);
        publisher.publish(Event.TOUR_LIST_DELETED_TOUR, idToDelete.toString());
    }

    private void queryTours(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            showAllTours();
        } else {
            changeTours(model.findByNameContains(searchTerm));
        }
    }

    private void changeTours(List<Tour> newTours) {
        tours = newTours;
        tourNames.setAll(tours.stream().map(Tour::getName).toList());
    }

    public ObservableList<String> getTourNames() {
        return tourNames;
    }

    public IntegerProperty selectedTourIndexProperty() {
        return selectedTourIndex;
    }
}
