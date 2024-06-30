package at.tiefenbrunner.swen2semesterprojekt.factories;

import at.tiefenbrunner.swen2semesterprojekt.repository.TourDatabaseRepository;
import at.tiefenbrunner.swen2semesterprojekt.service.ConfigService;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.util.TestDataGenerator;

public class ModelFactory {

    private static ModelFactory instance;

    private TourService model;
    private final ConfigService configService;

    private ModelFactory(ConfigService configService) {
        this.configService = configService;
    }

    public static ModelFactory getInstance(ConfigService configService) {
        if (null == instance) {
            instance = new ModelFactory(configService);
        }
        return instance;
    }

    public TourService getModel() {
        if (model == null) {
            TourDatabaseRepository repository = new TourDatabaseRepository(configService); // TODO: Move to Factory
            TestDataGenerator.setupTestData(repository); // TODO: Only for DEBUG builds

            model = new TourService(repository);
        }
        return model;
    }
}
