package at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels;

import at.tiefenbrunner.swen2semesterprojekt.data.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Publisher;

public class TourViewModel {
    private final Publisher publisher;
    private final TourService model;

    public TourViewModel(Publisher publisher, TourService model) {
        this.publisher = publisher;
        this.model = model;
    }
}
