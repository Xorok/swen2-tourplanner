package at.tiefenbrunner.swen2semesterprojekt.factories;

import at.tiefenbrunner.swen2semesterprojekt.repository.TourDatabaseRepository;
import at.tiefenbrunner.swen2semesterprojekt.service.ConfigService;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.service.route.OrsRouteService;
import at.tiefenbrunner.swen2semesterprojekt.util.TestDataGenerator;

public class ServiceFactory {

    private static ServiceFactory instance;

    private TourService tourService;
    private OrsRouteService orsRouteService;
    private final ConfigService configService;

    private ServiceFactory(ConfigService configService) {
        this.configService = configService;
    }

    public static ServiceFactory getInstance(ConfigService configService) {
        if (null == instance) {
            instance = new ServiceFactory(configService);
        }
        return instance;
    }

    public TourService getTourService() {
        if (tourService == null) {
            TourDatabaseRepository repository = new TourDatabaseRepository(configService); // TODO: Move to Factory
            TestDataGenerator.setupTestData(repository); // TODO: Only for DEBUG builds

            tourService = new TourService(repository);
        }
        return tourService;
    }

    public OrsRouteService getOrsRouteService() {
        if (orsRouteService == null)
            orsRouteService = new OrsRouteService(configService);
        return orsRouteService;
    }
}
