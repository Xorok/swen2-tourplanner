package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;
import java.util.UUID;

@Log4j2
public class TourDetailsViewModel {
    private final Publisher publisher;
    private final TourService model;

    private Tour tour;

    public TourDetailsViewModel(Publisher publisher, TourService model) {
        this.publisher = publisher;
        this.model = model;

        setupEvents();
    }

    private void setupEvents() {
        // on search event, update terms in list
        publisher.subscribe(Event.TOUR_LIST_TOUR_SELECTED, this::showTour);
    }

    private void showTour(String uuidStr) {
        try {
            UUID uuid = UUID.fromString(uuidStr);
            Optional<Tour> tourOpt = model.findById(uuid);
            if (tourOpt.isEmpty()) {
                log.info("Couldn't find selected Tour with ID {}", uuidStr);
            } else {
                tour = tourOpt.get();
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getStackTrace());
        }
    }
}
