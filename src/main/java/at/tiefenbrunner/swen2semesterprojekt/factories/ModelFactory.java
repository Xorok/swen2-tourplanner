package at.tiefenbrunner.swen2semesterprojekt.factories;

import at.tiefenbrunner.swen2semesterprojekt.repository.TourDbRepository;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;

public class ModelFactory {

    private static ModelFactory instance;

    private TourService model;

    private ModelFactory() {
    }

    public static ModelFactory getInstance() {
        if (null == instance) {
            instance = new ModelFactory();
        }
        return instance;
    }

    public TourService getModel() {
        if (model == null)
            model = new TourService(new TourDbRepository()); // TODO: Move to Factory
        return model;
    }
}
