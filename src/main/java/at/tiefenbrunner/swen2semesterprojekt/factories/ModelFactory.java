package at.tiefenbrunner.swen2semesterprojekt.factories;

import at.tiefenbrunner.swen2semesterprojekt.repository.TourDatabaseRepository;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.util.TestDataGenerator;

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
        if (model == null) {
            TourDatabaseRepository repository = new TourDatabaseRepository(); // TODO: Move to Factory
            TestDataGenerator.setupTestData(repository); // TODO: Only for DEBUG builds

            model = new TourService(repository);
        }
        return model;
    }
}
