package at.tiefenbrunner.swen2semesterprojekt.data;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.TourDatabaseRepository;
import at.tiefenbrunner.swen2semesterprojekt.data.service.*;
import at.tiefenbrunner.swen2semesterprojekt.data.service.route.OrsRouteService;

public class DataFactory {

    private static DataFactory instance;

    private TourDatabaseRepository tourDatabaseRepository;
    private TourService tourService;
    private TourReportService tourReportService;
    private JsonExportImportService jsonExportImportService;
    private OrsRouteService orsRouteService;
    private MapImageService mapImageService;
    private final ConfigService configService;

    private DataFactory(ConfigService configService) {
        this.configService = configService;
    }

    public static DataFactory getInstance(ConfigService configService) {
        if (null == instance) {
            instance = new DataFactory(configService);
        }
        return instance;
    }

    public ConfigService getConfigService() {
        return configService;
    }

    public TourService getTourService() {
        if (tourService == null) {
            tourService = new TourService(getTourDatabaseRepository(), getMapImageService(), getTourReportService(), getJsonExportImportService());
        }
        return tourService;
    }

    public TourReportService getTourReportService() {
        if (tourReportService == null) {
            tourReportService = new TourReportService();
        }
        return tourReportService;
    }

    public JsonExportImportService getJsonExportImportService() {
        if (jsonExportImportService == null) {
            jsonExportImportService = new JsonExportImportService();
        }
        return jsonExportImportService;
    }

    public TourDatabaseRepository getTourDatabaseRepository() {
        if (tourDatabaseRepository == null)
            tourDatabaseRepository = new TourDatabaseRepository(configService);
        return tourDatabaseRepository;
    }

    public OrsRouteService getOrsRouteService() {
        if (orsRouteService == null)
            orsRouteService = new OrsRouteService(configService);
        return orsRouteService;
    }

    public MapImageService getMapImageService() {
        if (mapImageService == null)
            mapImageService = new MapImageService();
        return mapImageService;
    }
}
