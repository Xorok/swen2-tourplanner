package at.tiefenbrunner.swen2semesterprojekt.factories;

import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.*;

public class ViewModelFactory {
    private static ViewModelFactory instance;

    private final Publisher publisher;

    // Factories
    private final ServiceFactory serviceFactory;

    // View-Models
    private MainViewModel mainViewModel;
    private SearchBarViewModel searchBarViewModel;
    private ToursListViewModel toursListViewModel;
    private TourViewModel tourViewModel;
    private TourDetailsViewModel tourDetailsViewModel;
    private MapViewModel mapViewModel;
    private TourLogsViewModel tourLogsViewModel;
    private TourLogDetailsViewModel tourLogDetailsViewModel;

    private ViewModelFactory(ServiceFactory serviceFactory) {
        publisher = new Publisher(); // TODO: Move to Factory
        this.serviceFactory = serviceFactory;
    }

    public static ViewModelFactory getInstance(ServiceFactory serviceFactory) {
        if (null == instance) {
            instance = new ViewModelFactory(serviceFactory);
        }
        return instance;
    }

    public MainViewModel getMainViewModel() {
        if (mainViewModel == null)
            mainViewModel = new MainViewModel(publisher, serviceFactory.getTourService());
        return mainViewModel;
    }

    public SearchBarViewModel getSearchBarViewModel() {
        if (searchBarViewModel == null)
            searchBarViewModel = new SearchBarViewModel(publisher, serviceFactory.getTourService());
        return searchBarViewModel;
    }

    public ToursListViewModel getTourListViewModel() {
        if (toursListViewModel == null)
            toursListViewModel = new ToursListViewModel(publisher, serviceFactory.getTourService());
        return toursListViewModel;
    }

    public TourViewModel getTourViewModel() {
        if (tourViewModel == null)
            tourViewModel = new TourViewModel(publisher, serviceFactory.getTourService());
        return tourViewModel;
    }

    public TourDetailsViewModel getTourDetailsViewModel() {
        if (tourDetailsViewModel == null)
            tourDetailsViewModel = new TourDetailsViewModel(publisher, serviceFactory.getTourService(), serviceFactory.getOrsRouteService());
        return tourDetailsViewModel;
    }

    public MapViewModel getMapViewModel() {
        if (mapViewModel == null)
            mapViewModel = new MapViewModel(publisher, serviceFactory.getTourService());
        return mapViewModel;
    }

    public TourLogsViewModel getTourLogsViewModel() {
        if (tourLogsViewModel == null)
            tourLogsViewModel = new TourLogsViewModel(publisher, serviceFactory.getTourService());
        return tourLogsViewModel;
    }

    public TourLogDetailsViewModel getTourLogDetailsViewModel() {
        if (tourLogDetailsViewModel == null)
            tourLogDetailsViewModel = new TourLogDetailsViewModel(publisher, serviceFactory.getTourService());
        return tourLogDetailsViewModel;
    }
}
