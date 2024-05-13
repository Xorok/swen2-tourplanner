package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;

public class TourLogsViewModel {
    private final Publisher publisher;
    private final TourService model;

    public TourLogsViewModel(Publisher publisher, TourService model) {
        this.publisher = publisher;
        this.model = model;
    }
}
