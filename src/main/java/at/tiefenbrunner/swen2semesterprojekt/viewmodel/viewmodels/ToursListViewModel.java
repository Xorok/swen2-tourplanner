package at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.data.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Publisher;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.UUID;

public class ToursListViewModel {

    private final Publisher publisher;
    private final TourService model;

    private List<Tour> tours;
    private final ObservableList<String> tourNames = FXCollections.observableArrayList();
    private final IntegerProperty selectedTourIndex = new SimpleIntegerProperty();

    public ToursListViewModel(Publisher publisher, TourService model) {
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
        publisher.subscribe(Event.SEARCH_TERM_SEARCHED, this::searchTours);
        publisher.subscribe(Event.TOUR_LIST_DELETED_TOUR, (deletedTourId) -> searchTours(""));
    }

    public void selectTour() {
        if (selectedTourIndex.get() == -1) {
            return;
        }

        publisher.publish(
                Event.TOUR_LIST_SELECTED_TOUR,
                tours.get(selectedTourIndex.get()).getId().toString()
        );
    }

    private void showAllTours() {
        changeTours(model.findAllTours());
    }

    public void addNew() {
        publisher.publish(Event.TOUR_LIST_CREATE_TOUR, "");
    }

    public void delete() {
        if (selectedTourIndex.get() == -1) {
            return;
        }

        UUID idToDelete = tours.get(selectedTourIndex.get()).getId();
        model.deleteTour(idToDelete);
        publisher.publish(Event.TOUR_LIST_DELETED_TOUR, idToDelete.toString());
    }

    private void searchTours(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            showAllTours();
        } else {
            changeTours(model.findTours(searchTerm));
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
